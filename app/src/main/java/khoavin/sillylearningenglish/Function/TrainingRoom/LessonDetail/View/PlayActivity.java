package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Presenter.LessonDetailPresenter;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.BackgroundMusicService;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.Service.NotificationControl;
import khoavin.sillylearningenglish.SYSTEM.Service.PLAYSTATE;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class PlayActivity extends AppCompatActivity {
    public Lesson lesson;
    LessonDetailPresenter lessonDetailPresenter;

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
    //endregion

    //region Process
    boolean isPlaying = false;
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
        lessonReadFragment.lesson = (Lesson)getIntent().getSerializableExtra("Lesson");
        lessonProgressFragment = new LessonProgressFragment();

        //region
        serviceIntent = new Intent(PlayActivity.this,BackgroundMusicService.class);
        bindService(serviceIntent,mConnection,BIND_AUTO_CREATE);
        //endregion
        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(serviceIntent);

        setUpTabAdapter();
        setUpOnClick();

        EventBus.getDefault().register(this);
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
                    //btnPlay.setBackgroundResource(R.drawable.ic_pause);
                }
                else {
                    EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PAUSE_ACTION));
                    isPlaying = false;
                    //btnPlay.setBackgroundResource(R.drawable.ic_play);
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
    public void onEvent(PLAYSTATE state) {
        switch (state){
            case IS_PLAYING:
            {
                isPlaying = true;
                btnPlay.setBackgroundResource(R.drawable.ic_pause);
            }
            break;
            case IS_PAUSE:{
                isPlaying = false;
                btnPlay.setBackgroundResource(R.drawable.ic_play);
            }
            break;
        }
    }
}
