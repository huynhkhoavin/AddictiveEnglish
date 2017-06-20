package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
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
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Object.LessonProgress;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonTracker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.BackgroundMusicService;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.Service.NotificationControl;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static android.content.Context.BIND_AUTO_CREATE;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURENT_MEDIA_PLAYER;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT_AMOUNT;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT_LIST;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.MUSIC_SERVICE_IS_RUNNING;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_LESSON_TRACKER;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.SERVER_URL;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonPlayFragment extends FragmentPattern {
    public Lesson lesson;
    public LessonUnit lessonUnit;

    @Inject
    IVolleyService volleyService;
    @Inject
    IAuthenticationService authenticationService;

    //region BindView
    @BindView(R.id.lesson_avatar) ImageView lessonAvatar;
    @BindView(R.id.tv_lessonTitle) TextView lessonTitle;
    @BindView(R.id.tv_lessonUnitTitle) TextView lessonUnitTitle;
    @BindView(R.id.tv_currentProgress) TextView tv_CurrentProgress;
    @BindView(R.id.tv_MaxProgress) TextView tv_MaxProgress;
    @BindView(R.id.sb_PlayingSeekBar) SeekBar SeekBar;
    @BindView(R.id.btn_Prev) ImageView btnPrev;
    @BindView(R.id.btn_Play) ImageView btnPlay;
    @BindView(R.id.btn_Next) ImageView btnNext;
    //endregion

    //region Process
    boolean isPlaying = false;
    Handler timerHandler = new Handler();
    int currentProgress=0;
    int duration = 0;
    MediaPlayer mMediaPlayer = new MediaPlayer();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            duration = mMediaPlayer.getDuration();
            if (duration == 0){
                duration = 1;
            }
            SeekBar.setProgress(mMediaPlayer.getCurrentPosition() * 100 / duration);
            tv_MaxProgress.setText(convert(mMediaPlayer.getDuration()));
            tv_CurrentProgress.setText(convert(currentProgress));
            timerHandler.postDelayed(timerRunnable, 1000);
            tv_CurrentProgress.setText(convert(mMediaPlayer.getCurrentPosition()));
        }
    };

    //endregion
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lesson_play,container,false);
        ButterKnife.bind(this,v);

        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);

        //show lesson Info
        showLessonInfo();

        //Start Service

        //Register onClick listener
        setUpOnClick();

        //Set up Progress
        setUpProgress();

        EventBus.getDefault().register(this);



        return v;
    }
    @Override
    public void onResume() {

        super.onResume();
        updateCurrentPlayInfo();
    }

    public void showLessonInfo(){
        lesson = (Lesson)Storage.getValue(CURRENT_LESSON);
        lessonTitle.setText(lesson.getLsTitle());
        lessonUnitTitle.setText("");
        Glide.with(getContext())
                .load(lesson.getLsAvatarUrl())
                .into(lessonAvatar);

    }
    private void setUpOnClick(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Storage.getInstance().CheckNodeIsExist(CURRENT_LESSON_UNIT) == false){
                    ArrayList<LessonUnit> list = (ArrayList<LessonUnit>)Storage.getInstance().getValue(CURRENT_LESSON_UNIT_LIST);
                    lessonUnit = list.get(0);
                    Storage.getInstance().addValue(CURRENT_LESSON_UNIT,lessonUnit);

                    EventBus.getDefault().post(new MessageEvent(Constants.ACTION.INIT_NEW_LESSON,SERVER_URL + lessonUnit.getLuUrl()));
                    return;
                }

                if (isPlaying == false) {
                    EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PLAY_ACTION));
                    isPlaying = true;
                    return;
                }
                else {
                    EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PAUSE_ACTION));
                    isPlaying = false;
                    return;
                }
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){

                    if (doubleBackToExitPressedOnce) {
                        // do something when double click
                        EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PREV_ACTION));
                        return;
                    }

                    doubleBackToExitPressedOnce = true;
                    // do something when click once
                    {
                        Toast.makeText(getContext(),"Double Click To Previous!",Toast.LENGTH_SHORT);
                        EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PLAY_BEGIN_ACTION));
                    }

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 1000);
                }
            }
        });
    }
    @Subscribe
    public void onEvent(final MediaPlayer mediaPlayer){

//Make sure you update Seekbar on UI thread
        this.mMediaPlayer = mediaPlayer;
        if(mediaPlayer.isPlaying()){
            btnPlay.setImageResource(R.drawable.music_pause);
            isPlaying = true;
            timerHandler.postDelayed(timerRunnable, 1000);
        }
        else
        {
            btnPlay.setImageResource(R.drawable.music_play);
            isPlaying = false;
            timerHandler.removeCallbacks(timerRunnable);
        }
    }

    public void setUpProgress(){
        mMediaPlayer = (MediaPlayer) Storage.getInstance().getValue(CURENT_MEDIA_PLAYER);
        if (mMediaPlayer==null) return;

        SeekBar.setMax(100);
        duration = mMediaPlayer.getDuration();
        if (duration == 0){
            duration = 1;
        }
        SeekBar.setProgress(mMediaPlayer.getCurrentPosition() * 100 / duration);

        tv_CurrentProgress.setText(convert(currentProgress));
        timerHandler.postDelayed(timerRunnable, 0);
        tv_CurrentProgress.setText(convert(mMediaPlayer.getCurrentPosition()));
        tv_MaxProgress.setText(convert(mMediaPlayer.getDuration()));
    }

    public String convert(long millis){
        String hms = String.format("%d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        //System.out.println(hms);
        return hms.substring(2);
    }
    public void updateCurrentPlayInfo() {
        if (Storage.getInstance().CheckNodeIsExist(CURRENT_LESSON_UNIT) && Storage.getInstance().CheckNodeIsExist(CURRENT_LESSON)) {
        lessonUnit = (LessonUnit) Storage.getInstance().getValue(CURRENT_LESSON_UNIT);
        lesson = (Lesson) Storage.getInstance().getValue(CURRENT_LESSON);
        Glide.with(getContext())
                .load(lesson.getLsAvatarUrl())
                .into(lessonAvatar);
        lessonTitle.setText(lesson.getLsTitle());
        lessonUnitTitle.setText(lessonUnit.getLuName());
    }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void LoadNewLesson(){
        if (Storage.getInstance().CheckNodeIsExist(MUSIC_SERVICE_IS_RUNNING)==true){
            Boolean music_isPlaying = (Boolean)Storage.getInstance().getValue(MUSIC_SERVICE_IS_RUNNING);
            if (music_isPlaying){
                return;
            }
            else
            {
                ArrayList<LessonUnit> lessonUnitArrayList = (ArrayList<LessonUnit>) Storage.getInstance().getValue(CURRENT_LESSON_UNIT_LIST);
                EventBus.getDefault().post(new MessageEvent(Constants.ACTION.ADD_URL,SERVER_URL + lessonUnitArrayList.get(0).getLuUrl()));
                Storage.getInstance().addValue(CURRENT_LESSON_UNIT,lessonUnitArrayList.get(0));

            }
        }
    }

}
