package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Presenter.LessonDetailPresenter;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonStorage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.BackgroundMusicService;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.Service.NotificationControl;
import khoavin.sillylearningenglish.SYSTEM.Service.PLAYSTATE;

import static khoavin.sillylearningenglish.Function.TrainingRoom.Home.TrainingHomeConstaint.HomeConstaint.CURENT_MEDIA_PLAYER;
import static khoavin.sillylearningenglish.Function.TrainingRoom.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class PlayActivity extends AppCompatActivity {
    public Lesson lesson;
    LessonDetailPresenter lessonDetailPresenter;
    public String TAG = "Play Activity";
    //Region View
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ViewPagerAdapter tabPagerAdapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    LessonReadFragment lessonReadFragment;
    LessonProgressFragment lessonProgressFragment;
    //endregion

    //Region Playing Bar
    @BindView(R.id.btnPlay)
    Button btnPlay;
    @BindView(R.id.btnPrev)
    Button btnPrev;
    @BindView(R.id.btnNext)
    Button btnNext;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.currentPosition)
    TextView currentPosition;
    @BindView(R.id.maxPosition)
    TextView maxPosition;
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
                progressBar.setMax(100);
                duration = mMediaPlayer.getDuration();
            if (duration == 0){
                duration = 1;
            }
                progressBar.setProgress(mMediaPlayer.getCurrentPosition() * 100 / duration);
                maxPosition.setText(convert(mMediaPlayer.getDuration()));
                currentPosition.setText(convert(currentProgress));
                timerHandler.postDelayed(timerRunnable, 1000);
                currentPosition.setText(convert(mMediaPlayer.getCurrentPosition()));
        }
    };

    //endregion

    //region Service
    BackgroundMusicService backgroundMusicService;
    NotificationControl notificationControl;
    boolean mBounder;
    Intent serviceIntent;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getApplicationContext(),"Music starting...",Toast.LENGTH_SHORT).show();
            mBounder = true;
            BackgroundMusicService.LocalBinder mLocalBinder = (BackgroundMusicService.LocalBinder)service;
            backgroundMusicService = mLocalBinder.getServiceInstance();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(),"Music stopping...",Toast.LENGTH_LONG).show();
            mBounder = false;
            backgroundMusicService = null;
        }
    };
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        lessonReadFragment = new LessonReadFragment();
        lessonReadFragment.lesson = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
        mMediaPlayer = (MediaPlayer)Storage.getInstance().getValue(CURENT_MEDIA_PLAYER);
        lessonProgressFragment = new LessonProgressFragment();

        //region
        serviceIntent = new Intent(PlayActivity.this,BackgroundMusicService.class);
        bindService(serviceIntent,mConnection,BIND_AUTO_CREATE);
        //endregion
        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(serviceIntent);

        setUpTabAdapter();
        setUpOnClick();
        setUpProgress();
        EventBus.getDefault().register(this);

        //
    }
    private void setUpTabAdapter(){
        String[] TabTitle = {"Play","Tiến Trình"};
        FragmentPattern[] FragmentList = {lessonReadFragment,lessonProgressFragment};
        tabPagerAdapter = new ViewPagerAdapter(((AppCompatActivity)this).getSupportFragmentManager(),TabTitle,FragmentList);
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
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
    private void setUpServiceIntent(){
        //region
        serviceIntent = new Intent(PlayActivity.this,BackgroundMusicService.class);
        bindService(serviceIntent,mConnection,BIND_AUTO_CREATE);
        //endregion
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEvent(final MediaPlayer mediaPlayer){

//Make sure you update Seekbar on UI thread
        this.mMediaPlayer = mediaPlayer;

        if(mediaPlayer.isPlaying()){
            btnPlay.setBackgroundResource(R.drawable.ic_pause);
            isPlaying = true;
            timerHandler.postDelayed(timerRunnable, 1000);
        }
        else
        {
            btnPlay.setBackgroundResource(R.drawable.ic_play);
            isPlaying = false;
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
    public String convert(long millis){
        String hms = String.format("%d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        //System.out.println(hms);
        return hms.substring(2);
    }
    public void setUpProgress(){
        mMediaPlayer = (MediaPlayer)Storage.getInstance().getValue(CURENT_MEDIA_PLAYER);
        if (mMediaPlayer==null) return;
        progressBar.setMax(100);
        duration = mMediaPlayer.getDuration();
        if (duration == 0){
            duration = 1;
        }
        progressBar.setProgress(mMediaPlayer.getCurrentPosition() * 100 / duration);

        currentPosition.setText(convert(currentProgress));
        timerHandler.postDelayed(timerRunnable, 0);
        currentPosition.setText(convert(mMediaPlayer.getCurrentPosition()));
        maxPosition.setText(convert(mMediaPlayer.getDuration()));
    }
}
