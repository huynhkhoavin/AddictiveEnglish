package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
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

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.BackgroundMusicService;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.Service.NotificationControl;

import static android.content.Context.BIND_AUTO_CREATE;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURENT_MEDIA_PLAYER;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.MUSIC_SERVICE_IS_RUNNING;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonPlayFragment extends FragmentPattern {
    public Lesson lesson;
    public LessonUnit lessonUnit;
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

    //region Service
    BackgroundMusicService backgroundMusicService;
    boolean mBounder;
    Intent serviceIntent;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getContext(),"Music starting...",Toast.LENGTH_SHORT).show();
            mBounder = true;
            BackgroundMusicService.LocalBinder mLocalBinder = (BackgroundMusicService.LocalBinder)service;
            backgroundMusicService = mLocalBinder.getServiceInstance();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getContext(),"Music stopping...",Toast.LENGTH_LONG).show();
            mBounder = false;
            backgroundMusicService = null;
        }
    };
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lesson_play,container,false);
        ButterKnife.bind(this,v);

        //show lesson Info
        showLessonInfo();

        //Start Service
        startService();

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
                if (isPlaying == false) {
                    EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PLAY_ACTION));
                    isPlaying = true;
                }
                else {
                    EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PAUSE_ACTION));
                    isPlaying = false;
                }
            }
        });
    }
    private void startService() //Start Music Service
    {
//        if ((Boolean) Storage.getInstance().getValue(MUSIC_SERVICE_IS_RUNNING)==null  ) {
            Storage.getInstance().addValue(MUSIC_SERVICE_IS_RUNNING, true);
            serviceIntent = new Intent(getActivity(), BackgroundMusicService.class);
            getActivity().bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
            serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            getActivity().startService(serviceIntent);
//        }
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
}
