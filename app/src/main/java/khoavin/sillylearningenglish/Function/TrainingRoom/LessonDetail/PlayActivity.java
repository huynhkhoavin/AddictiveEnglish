package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.LessonPlayFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.LessonProgressFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.TabIconPagerAdapter;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.BackgroundMusicService;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.Service.NotificationControl;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURENT_MEDIA_PLAYER;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class PlayActivity extends AppCompatActivity {
    public Lesson lesson;
    public String TAG = "Play Activity";
    //Region View
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TabIconPagerAdapter tabPagerAdapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    LessonPlayFragment lessonReadFragment;
    LessonProgressFragment lessonProgressFragment;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);


        //region
//        serviceIntent = new Intent(PlayActivity.this,BackgroundMusicService.class);
        //bindService(serviceIntent,mConnection,BIND_AUTO_CREATE);
        //endregion
//        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
//        startService(serviceIntent);

        setUpTabAdapter();
//        setUpOnClick();
//        setUpProgress();
//        EventBus.getDefault().register(this);

        //
    }
    private void setUpTabAdapter(){
        lessonReadFragment = new LessonPlayFragment();
        lessonProgressFragment = new LessonProgressFragment();
        FragmentPattern[] FragmentList = {lessonReadFragment,lessonProgressFragment};
        tabPagerAdapter = new TabIconPagerAdapter(((AppCompatActivity)this).getSupportFragmentManager(),getApplicationContext(),FragmentList,new int[]{R.drawable.ic_audio_book,R.drawable.ic_progress}, new String[]{"Play","Progress"});
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
//    private void setUpOnClick(){
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isPlaying == false) {
//                    EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PLAY_ACTION));
//                    isPlaying = true;
//                }
//                else {
//                    EventBus.getDefault().post(new MessageEvent(Constants.ACTION.PAUSE_ACTION));
//                    isPlaying = false;
//                }
//            }
//        });
//    }
//    private void setUpServiceIntent(){
//        //region
//        serviceIntent = new Intent(PlayActivity.this,BackgroundMusicService.class);
//        bindService(serviceIntent,mConnection,BIND_AUTO_CREATE);
//        //endregion
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
