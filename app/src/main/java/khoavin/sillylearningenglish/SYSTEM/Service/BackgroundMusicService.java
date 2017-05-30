package khoavin.sillylearningenglish.SYSTEM.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonTracker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.Pattern.ProgressThreadTask;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURENT_MEDIA_PLAYER;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT_AMOUNT;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.MUSIC_SERVICE_IS_RUNNING;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_LESSON_TRACKER;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.UPDATE_LESSON_UNIT;
import static khoavin.sillylearningenglish.SYSTEM.Service.Constants.ACTION.UPDATE_PROGRESS_SUCCESS;

/**
 * Created by Dev02 on 3/6/2017.
 */

public class BackgroundMusicService extends Service {
    @Inject
    IVolleyService volleyService;
    @Inject
    IAuthenticationService authenticationService;
    private final String LOG_TAG = "BackgroundMusicService";
    private final IBinder mBinder = new LocalBinder();
    private NotificationControl notificationControl;
    private int update_flag_point = 0; // duration for once update time
    private int tracked_point = 0; // updated point of last update
    PLAYSTATE playState;
    Handler timerHandler = new Handler();
    Runnable lessonTrackerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer.isPlaying()){
                int currentProgressRatio = mMediaPlayer.getCurrentPosition()/update_flag_point;
                if(currentProgressRatio>tracked_point) // neu nhu progress lon hon tracked point va chua track
                {
                    UpdateLesson();
                    tracked_point = currentProgressRatio;
                }
            }
            timerHandler.postDelayed(lessonTrackerRunnable, 1000);
            if (!mMediaPlayer.isPlaying()){
                timerHandler.removeCallbacks(this);
            }
        }
    };

    public class LocalBinder extends Binder{
        public BackgroundMusicService getServiceInstance(){
            return BackgroundMusicService.this;
        }
    }
    private MediaPlayer mMediaPlayer = null;
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public void release(){
//        if (mMediaPlayer!=null) {
            mMediaPlayer.stop();
//            mMediaPlayer = null;
//        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        ((SillyApp) this.getApplication()).getDependencyComponent().inject(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (notificationControl==null)
        {
            notificationControl = new NotificationControl(getApplicationContext());
            mMediaPlayer = new MediaPlayer();
            notificationControl.showNotification();
        }
        else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {

        }
        else if (intent.getAction().equals(Constants.ACTION.MAIN_ACTION)) {

        }

        else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            playAction();
        } else if (intent.getAction().equals(Constants.ACTION.PAUSE_ACTION)) {
            pauseAction();
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {

        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            stopAction();
        }
        return START_STICKY;
    }


    public void playAction(){
        mMediaPlayer.start();
        playState = PLAYSTATE.IS_PLAYING;
        EventBus.getDefault().post(mMediaPlayer); // post to progress

        timerHandler.postDelayed(lessonTrackerRunnable, 1000);
    }
    public void pauseAction(){
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            playState = PLAYSTATE.IS_PAUSE;
            EventBus.getDefault().post(mMediaPlayer);
            timerHandler.removeCallbacks(lessonTrackerRunnable);
        }
    }
    public void stopAction(){
        Log.i(LOG_TAG, "Received Stop Foreground Intent");
        Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(mMediaPlayer);
        release();
        Storage.getInstance().addValue(MUSIC_SERVICE_IS_RUNNING,false);
        notificationControl.CancelAll();

        timerHandler.removeCallbacks(lessonTrackerRunnable);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEvent(MessageEvent event) {
        switch (event.getMessage()){
            case Constants.ACTION.PLAY_ACTION:{
                playAction();
                break;
            }
            case Constants.ACTION.ADD_URL:{
                notificationControl.Notify();
                if (mMediaPlayer!=null) {
                    mMediaPlayer.reset();
                    mMediaPlayer.stop();
                }
                try {
                    //mMediaPlayer.release();
                    mMediaPlayer.setDataSource(event.getUrl());
                    mMediaPlayer.prepare();
                    Storage.getInstance().addValue(CURENT_MEDIA_PLAYER,mMediaPlayer);
                    CalculateLocationArray();
                    playAction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case Constants.ACTION.PAUSE_ACTION:{
                pauseAction();
                break;
            }
            case Constants.ACTION.STOPFOREGROUND_ACTION:{
                stopAction();
                break;
            }
        }
    }

    private void CalculateLocationArray(){
        update_flag_point = mMediaPlayer.getDuration()/5;
    }
    public void UpdateLesson(){
        ProgressThreadTask progressThreadTask = new ProgressThreadTask() {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_LESSON_UNIT,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", authenticationService.getCurrentUser().getUid());

                        Lesson ls = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
                        params.put("ls_id",String.valueOf(ls.getLsId()));

                        LessonUnit lu = (LessonUnit)Storage.getInstance().getValue(CURRENT_LESSON_UNIT);
                        params.put("ls_progress",String.valueOf(5*lu.getLuSequence()+tracked_point));

                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };
        progressThreadTask.execute();
    }
    public void GetLessonTracker(){
        ProgressThreadTask progressThreadTask = new ProgressThreadTask() {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LESSON_TRACKER,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                LessonTracker[] lessonTrackers = JsonConvert.getArray(response,LessonTracker[].class);
                                int lessonUnitAmount= (int)Storage.getInstance().getValue(CURRENT_LESSON_UNIT_AMOUNT);
                                if(lessonTrackers[0].getProgress()%(lessonUnitAmount)==0){

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", authenticationService.getCurrentUser().getUid());

                        Lesson ls = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
                        params.put("ls_id",String.valueOf(ls.getLsId()));

                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };
        progressThreadTask.execute();
    }
    public void PostNotification(){
        ProgressThreadTask progressThreadTask = new ProgressThreadTask() {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LESSON_TRACKER,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                LessonTracker[] lessonTrackers = JsonConvert.getArray(response,LessonTracker[].class);
                                int lessonUnitAmount= (int)Storage.getInstance().getValue(CURRENT_LESSON_UNIT_AMOUNT);
                                if(lessonTrackers[0].getProgress()%(lessonUnitAmount)==0){

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", authenticationService.getCurrentUser().getUid());

                        Lesson ls = (Lesson)Storage.getInstance().getValue(CURRENT_LESSON);
                        params.put("ls_id",String.valueOf(ls.getLsId()));

                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };
        progressThreadTask.execute();
    }
}

