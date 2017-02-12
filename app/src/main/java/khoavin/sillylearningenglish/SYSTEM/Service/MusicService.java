package khoavin.sillylearningenglish.SYSTEM.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.WEBSERVICE_ADDRESS;

/**
 * Created by KhoaVin on 2/5/2017.
 */

public class MusicService extends Service {
    public static final String ACTION_PLAY = "action_play";



    private final IBinder mBinder = new ServiceBinder();

    public class ServiceBinder extends Binder {
        public MusicService getService()
        {
            return MusicService.this;
        }
    }
    private MediaPlayer player;
    private int length = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    public void playMusic(){
        final String url = WEBSERVICE_ADDRESS+"listen/01.mp3";
        player = MediaPlayer.create(this, Uri.parse(url));
        player.setLooping(true);
        player.start();
    }
    public void pauseMusic()
    {
        if(player.isPlaying())
        {
            player.pause();
            length=player.getCurrentPosition();

        }
    }

    public void resumeMusic()
    {
        if(player.isPlaying()==false)
        {
            player.seekTo(length);
            player.start();
        }
    }

    public void stopMusic()
    {
        player.stop();
        player.release();
        player = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
