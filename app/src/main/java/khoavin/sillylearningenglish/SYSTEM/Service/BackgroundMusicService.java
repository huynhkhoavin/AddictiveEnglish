package khoavin.sillylearningenglish.SYSTEM.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SingleViewObject.ProgressUnit;

import static khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils.BASE_URL;

/**
 * Created by Dev02 on 3/6/2017.
 */

public class BackgroundMusicService extends Service {
    private final String LOG_TAG = "BackgroundMusicService";
    private final IBinder mBinder = new LocalBinder();
    private NotificationControl notificationControl;
    PLAYSTATE playState;
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
        if (mMediaPlayer!=null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
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
        else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            playAction();
        } else if (intent.getAction().equals(Constants.ACTION.PAUSE_ACTION)) {
            pauseAction();
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {

        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {

        }

        notificationControl.Notify();
        return START_STICKY;
    }


    public void playAction(){
        mMediaPlayer.start();
        playState = PLAYSTATE.IS_PLAYING;
        EventBus.getDefault().post(mMediaPlayer); // post to progress
    }
    public void pauseAction(){
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            playState = PLAYSTATE.IS_PAUSE;
            EventBus.getDefault().post(mMediaPlayer);
        }
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
                if (mMediaPlayer!=null) {
                    mMediaPlayer.reset();
                    mMediaPlayer.stop();
                }
                try {
                    //mMediaPlayer.release();
                    mMediaPlayer.setDataSource(event.getUrl());
                    mMediaPlayer.prepare();
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
        }
    }
}

