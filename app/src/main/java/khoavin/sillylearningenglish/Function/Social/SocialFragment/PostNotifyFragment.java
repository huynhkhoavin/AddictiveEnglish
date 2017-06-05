package khoavin.sillylearningenglish.Function.Social.SocialFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.EventListener.PostNotifyListener;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ISocialNetworkService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.POST_NOTIFICATION;

/**
 * Created by KhoaVin on 05/06/2017.
 */

public class PostNotifyFragment extends DialogFragment {
    @Inject
    ISocialNetworkService socialNetworkService;
    @Inject
    IAuthenticationService authenticationService;
    @Inject
    IVolleyService volleyService;
    @BindView(R.id.imgAvatar)
    ImageView userAvatar;
    @BindView(R.id.edt_statusContent)
    EditText edtStatusContent;
    @BindView(R.id.btn_Post) ImageView btnSend;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_post_status, container, false);
        ButterKnife.bind(this,rootView);

        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);


        Glide.with(getContext()).load(authenticationService.getCurrentUser().getPhotoUrl()).placeholder(R.drawable.avatar_holder)
                .into(userAvatar);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtStatusContent.getText().equals("")==false){
                    socialNetworkService.postNotification(edtStatusContent.getText().toString(), new PostNotifyListener() {
                        @Override
                        public void onPostSuccess(Notification notification) {
                            Toast.makeText(getContext(),"Post new status success!",Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                            EventBus.getDefault().post(new MessageEvent("POST_SUCCESS"));
                        }

                        @Override
                        public void onPostError(String ErrorMessage) {

                        }
                    });
                }
            }
        });
        return rootView;
    }

    public void postStatus(){

    }
}
