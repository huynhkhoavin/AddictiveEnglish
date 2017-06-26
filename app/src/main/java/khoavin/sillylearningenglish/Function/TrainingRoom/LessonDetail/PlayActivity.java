package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment.LessonPlayFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment.LessonProgressFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.Pattern.TabIconPagerAdapter;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.BackgroundMusicService;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT_LIST;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.MUSIC_SERVICE_IS_RUNNING;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_LESSON_UNIT;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class PlayActivity extends AppCompatActivity {
    public Lesson lesson;
    public String TAG = "Play Activity";
    @Inject
    IVolleyService volleyService;
    ArrayList<LessonUnit> lessonUnits;
    //Region View
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TabIconPagerAdapter tabPagerAdapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    LessonPlayFragment lessonPlayFragment;
    LessonProgressFragment lessonProgressFragment;
    //endregion

    //region Service
    BackgroundMusicService backgroundMusicService;
    boolean mBounder = false;
    Intent serviceIntent;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getApplicationContext(), "Music starting...", Toast.LENGTH_SHORT).show();
            mBounder = true;
            BackgroundMusicService.LocalBinder mLocalBinder = (BackgroundMusicService.LocalBinder) service;
            backgroundMusicService = mLocalBinder.getServiceInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(), "Music stopping...", Toast.LENGTH_LONG).show();
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
        lesson = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
        ((SillyApp) this.getApplication()).getDependencyComponent().inject(this);
        getLessonUnits();

        // Get Shared Preferences
        if (checkServiceIsRunning() == false){
            LoadPreferences();
            startService();
        }

    }
    private void setUpTabAdapter(){
        lessonPlayFragment = new LessonPlayFragment();
        lessonProgressFragment = new LessonProgressFragment();
        FragmentPattern[] FragmentList = {lessonPlayFragment,lessonProgressFragment};
        tabPagerAdapter = new TabIconPagerAdapter(((AppCompatActivity)this).getSupportFragmentManager(),getApplicationContext(),FragmentList,new int[]{R.drawable.ic_audio_book,R.drawable.ic_progress}, new String[]{"Play","Progress"});
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
    }


    public void getLessonUnits(){
        NetworkAsyncTask networkProgress = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                lessonUnits = ArrayConvert.toArrayList(JsonConvert.getArray(response,LessonUnit[].class));
                Storage.getInstance().addValue(CURRENT_LESSON_UNIT_LIST,lessonUnits);
                setUpTabAdapter();
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ls_id",lesson.getLsId());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_LESSON_UNIT;
            }
        };
        networkProgress.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void startService() //Start Music Service
    {
        Boolean ServiceIsRunning = false;
        if (Storage.getInstance().CheckNodeIsExist(MUSIC_SERVICE_IS_RUNNING)){
            ServiceIsRunning = (Boolean)Storage.getInstance().getValue(MUSIC_SERVICE_IS_RUNNING);
        }
        if (ServiceIsRunning == false) {
            Storage.getInstance().addValue(MUSIC_SERVICE_IS_RUNNING, true);
            serviceIntent = new Intent(this, BackgroundMusicService.class);
            this.bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
            serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            this.startService(serviceIntent);

            LoadPreferences();
        }
    }

    public void LoadPreferences(){
        SharedPreferences sharedPreferences= this.getSharedPreferences(Constants.SHARED_PREFERENCES.MUSIC_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences!=null){
            int lessonProgress = sharedPreferences.getInt(CURRENT_LESSON + ":" + lesson.getLsId(),0);
            //EventBus.getDefault().post(new MessageEvent(Constants.ACTION.RESUME_LAST_PROGRESS,lessonProgress));
        }
    }

    public boolean checkServiceIsRunning(){
        Boolean b = (Boolean)Storage.getInstance().getValue(MUSIC_SERVICE_IS_RUNNING);
        if (b==null)return false;
        else
            return (b==true);
    }

}
