package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Object.LessonProgress;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.ILessonDetailView;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.ProgressListAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.Reading.ReadActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.EventListener.PostNotifyListener;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ISocialNetworkService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonTracker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.ConnectDialog;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.Pattern.YesNoDialog;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.BackgroundMusicService;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static android.content.Context.BIND_AUTO_CREATE;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.*;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.*;
import static khoavin.sillylearningenglish.SYSTEM.Service.Constants.ACTION.UPDATE_PROGRESS_SUCCESS;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonProgressFragment extends FragmentPattern implements ILessonDetailView {

    ViewPager parentViewPager;

    //region BindView
    @BindView(R.id.recyclerView) RecyclerView recycleView;
    @BindView(R.id.lesson_image) ImageView lessonAvatar;
    @BindView(R.id.author_name)
    TextView tvAuthorName;
    @BindView(R.id.btn_Read)
    Button btnRead;
    @BindView(R.id.lesson_title)
    TextView lessonTitle;

    //endregion

    //region Inject
    private ProgressListAdapter adapter;
    @Inject
    ITrainingService trainingService;
    @Inject
    IVolleyService volleyService;
    @Inject
    ISocialNetworkService socialNetworkService;
    Lesson lesson;
    @Inject
    IAuthenticationService authenticationService;
    //endregion
    //region Service
    BackgroundMusicService backgroundMusicService;
    boolean mBounder = false;
    Intent serviceIntent;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getContext(), "Music starting...", Toast.LENGTH_SHORT).show();
            mBounder = true;
            BackgroundMusicService.LocalBinder mLocalBinder = (BackgroundMusicService.LocalBinder) service;
            backgroundMusicService = mLocalBinder.getServiceInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getContext(), "Music stopping...", Toast.LENGTH_LONG).show();
            mBounder = false;
            backgroundMusicService = null;
        }
    };
    //endregion

    ArrayList<LessonUnit> lessonUnits;

    public void setParentViewPager(ViewPager parentViewPager) {
        this.parentViewPager = parentViewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_lesson_detail_progress,container,false);
            ButterKnife.bind(this,v);
            ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);
            setUpAdapter();

            lessonUnits = (ArrayList<LessonUnit>)Storage.getInstance().getValue(CURRENT_LESSON_UNIT_LIST);

            getProgress(lessonUnits);


            showLessonInfo();

            if (mBounder==false){
                EventBus.getDefault().register(this);
                startService();
            }
        return v;
    }
    public void showLessonInfo(){
        lesson = (Lesson)Storage.getValue(CURRENT_LESSON);
        tvAuthorName.setText(lesson.getLsAuthor());
        lessonTitle.setText(lesson.getLsTitle());
        Glide.with(getContext())
                .load(lesson.getLsAvatarUrl())
                .into(lessonAvatar);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lesson ls = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
                String url = ls.getLsFileUrl();
                Intent intent = new Intent(getActivity(),ReadActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getProgress(final ArrayList<LessonUnit> lessonUnits){
        Storage.getInstance().addValue(CURRENT_LESSON_UNIT_AMOUNT,lessonUnits.size());

        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                LessonTracker[] lessonTrackers = JsonConvert.getArray(response,LessonTracker[].class);
                LessonProgress lessonProgress = new LessonProgress(lessonUnits,lessonTrackers[0]);

                ShowProgress(lessonUnits,lessonTrackers[0].getProgress());
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ls_id",((Lesson)Storage.getValue(CURRENT_LESSON)).getLsId());
                params.put("user_id",authenticationService.getCurrentUser().getUid());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_LESSON_TRACKER;
            }
        };
        networkAsyncTask.execute();
    }
    @Override
    public void ShowProgress(ArrayList<LessonUnit> lessonUnits, int progress) {
        //tinh toan de hien thi progress
        int progressSequence = progress / 5;
        adapter.setProgressSequence(progressSequence);

        adapter.setDataSource(ArrayConvert.toObjectArray(lessonUnits));
    }
    public void setUpAdapter(){
        adapter = new ProgressListAdapter(getActivity(), new ArrayList<>());
        adapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {

                //parentViewPager.setCurrentItem(0);
                LessonUnit lessonUnit = (LessonUnit) ItemObject;
                EventBus.getDefault().post(new MessageEvent(Constants.ACTION.ADD_URL,SERVER_URL + lessonUnit.getLuUrl()));
                Storage.getInstance().addValue(CURRENT_LESSON_UNIT,lessonUnit);

                adapter.setCurrentPlayingSeuquence(lessonUnit.getLuSequence());

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setNestedScrollingEnabled(false);
    }
    @Subscribe
    public void onEvent(String Message){
        if (Message == UPDATE_PROGRESS_SUCCESS){
            getProgress(lessonUnits);

            LessonUnit lessonUnit = (LessonUnit)Storage.getInstance().getValue(CURRENT_LESSON_UNIT);
            final Lesson lesson = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
            ArrayList<LessonUnit> listLessonUnit = (ArrayList<LessonUnit>)Storage.getInstance().getValue(CURRENT_LESSON_UNIT_LIST);
            if (lessonUnit.getLuSequence()==listLessonUnit.get(listLessonUnit.size()-1).getLuSequence()){
                final YesNoDialog yesNoDialog = new YesNoDialog();
                yesNoDialog.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(),"yes no dialog");
                yesNoDialog.setMessage("You're already unlock all unit of this lesson. Do you want to post notify to your friend?");
                yesNoDialog.setOnPositiveListener(new ConnectDialog.Listener() {
                    @Override
                    public void onClick() {
                        socialNetworkService.postNotification("Hahaha! I already unlock: " + lesson.getLsTitle(), new PostNotifyListener() {
                            @Override
                            public void onPostSuccess(Notification notification) {
                                Toast.makeText(getContext(),"Post notify success!",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPostError(String ErrorMessage) {
                                Toast.makeText(getContext(),"Post notify failed. Retry manually!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }
    @Subscribe
    public void onEvent(final MessageEvent messageEvent){
        if (messageEvent.getMessage().equals(Constants.MESSAGE_EVENT.UPDATE_PROGRESS)){
            getProgress(lessonUnits);

            Toast.makeText(getContext(), "Congratulation! You're already unlock new lesson unit!", Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe
    public void onEvent(final MediaPlayer mediaPlayer){
        if (mediaPlayer.isPlaying()==false)
        adapter.setCurrentPlayingSeuquence(-1);
        else
        {
            LessonUnit lessonUnit = (LessonUnit)Storage.getInstance().getValue(CURRENT_LESSON_UNIT);
            adapter.setCurrentPlayingSeuquence(lessonUnit.getLuSequence());
        }
    }


    public String convert(long millis){
        String hms = String.format("%d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        //System.out.println(hms);
        return hms.substring(2);
    }

    private void startService() //Start Music Service
    {
        Boolean ServiceIsRunning = (Boolean)Storage.getInstance().getValue(MUSIC_SERVICE_IS_RUNNING);
        if (ServiceIsRunning == false) {
            Storage.getInstance().addValue(MUSIC_SERVICE_IS_RUNNING, true);
            serviceIntent = new Intent(getActivity(), BackgroundMusicService.class);
            getActivity().bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
            serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            getActivity().startService(serviceIntent);
        }
    }
}
