package khoavin.sillylearningenglish.Function.Social.SocialFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Social.View.CommentAdapter;
import khoavin.sillylearningenglish.NetworkService.EventListener.CommentListener;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ISocialNetworkService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Comment;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_NOTIFY_COMMENTS;

public class NotifyDetailFragment extends DialogFragment {
    public Notification clickedNotify;
    CommentAdapter commentAdapter;
    public final String TAG = "NotifyDetailFragment";
    @Inject
    IVolleyService volleyService;
    @Inject
    ISocialNetworkService socialNetworkService;
    //region BindView
    @BindView(R.id.img_user_avatar)
    ImageView userAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_location) TextView tvUserLocation;
    @BindView(R.id.post_time) TextView tvPostTime;
    @BindView(R.id.tv_notify_content)
    ExpandableTextView tvContent;
    @BindView(R.id.button_toggle)
    Button btn_Toogle;
    @BindView(R.id.tv_likeCount) TextView tvLikeCount;
    @BindView(R.id.tv_CommentCount) TextView tvCommentCount;
    @BindView(R.id.btn_like) ImageView btnLike;
    @BindView(R.id.btn_comment) ImageView btnComment;
    @BindView(R.id.listComment) RecyclerView listComment;
    @BindView(R.id.edt_WriteComment) EditText edtWriteComment;
    @BindView(R.id.btnDoComment) ImageView btnDoComment;
    //endregion

    @SuppressLint("ValidFragment")
    public NotifyDetailFragment(Notification clickedNotify){
        this.clickedNotify = clickedNotify;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_notify_detail, container, false);
        ButterKnife.bind(this,rootView);


        getDialog().setTitle("Simple Dialog");

        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);

        LoadDetail();

        loadListComment();

        doComment();

        setUpAdapter();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void LoadDetail(){

        Glide.with(getContext())
                .load(clickedNotify.getAvatarUrl())
                .into(userAvatar);

        tvUserName.setText(clickedNotify.getName());

        tvUserLocation.setText(clickedNotify.getUserLocation());

        tvContent.setText(clickedNotify.getNotifyContent());

        tvLikeCount.setText(clickedNotify.getLikeCount());

        tvCommentCount.setText(clickedNotify.getCommentCount());

        tvContent.setAnimationDuration(300L);

        // set interpolators for both expanding and collapsing animations
        tvContent.setInterpolator(new AccelerateDecelerateInterpolator());


// toggle the ExpandableTextView
        btn_Toogle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                tvContent.toggle();
                btn_Toogle.setText(tvContent.isExpanded() ? R.string.collapse : R.string.expand);
            }
        });

    }
    public void loadListComment(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                Comment[] comments = JsonConvert.getArray(response,Comment[].class);
                showListComment(ArrayConvert.toArrayList(comments));
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("notify_id",String.valueOf(clickedNotify.getNotifyId()));
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_NOTIFY_COMMENTS;
            }
        };
        networkAsyncTask.execute();
    }

    public void showListComment(ArrayList<Comment> list){
        commentAdapter.setDataSource(ArrayConvert.toObjectArray(list));
    }

    public void setUpAdapter(){
        commentAdapter = new CommentAdapter(getActivity(), new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listComment.setLayoutManager(linearLayoutManager);
        listComment.setAdapter(commentAdapter);
        listComment.setNestedScrollingEnabled(false);
    }

    public void doComment(){
        btnDoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtWriteComment.getText().equals("")==false){
                    Comment cmt = new Comment(clickedNotify.getNotifyId(),edtWriteComment.getText().toString());
                    socialNetworkService.doComment(cmt, new CommentListener() {
                        @Override
                        public void commentSuccess() {
                            Log.e(TAG, "Comment Success");
                            loadListComment();
                            edtWriteComment.setText("");
                        }

                        @Override
                        public void commentError() {

                        }
                    });
                }
            }
        });
    }
}