package khoavin.sillylearningenglish.FUNCTION.TrainingRoom;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import khoavin.sillylearningenglish.PATTERN.TabFragmentPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.MediaPlayerService;
import khoavin.sillylearningenglish.SYSTEM.Service.MusicService;

/**
 * Created by KhoaVin on 1/26/2017.
 */

public class PlayingFragment extends TabFragmentPattern {
    MediaPlayer mPlayer;
    Button buttonPlay;
    Button buttonResume;
    Button buttonStop;
    MusicService musicService;
    Intent serviceIntent;
    boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.ServiceBinder binder = (MusicService.ServiceBinder) service;
            musicService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.playing_fragment,container,false);

        //region service cũ
        buttonPlay = (Button)v.findViewById(R.id.play);
        buttonResume = (Button)v.findViewById(R.id.resume);
        buttonStop = (Button)v.findViewById(R.id.stop);

//        serviceIntent = new Intent(getContext(), MusicService.class);
//        getActivity().bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
//        getActivity().startService(serviceIntent);
        Intent intent = new Intent( getContext(), MediaPlayerService.class );
        intent.setAction( MediaPlayerService.ACTION_PLAY );
        getActivity().startService( intent );
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == buttonPlay)
                {
                    //musicService.playMusic();
                }
                else if(v == buttonStop){
                    //musicService.stopMusic();
                }
                else if(v == buttonResume){
                    //musicService.resumeMusic();
                }
            }
        };
        buttonPlay.setOnClickListener(listener);
        buttonResume.setOnClickListener(listener);
        buttonStop.setOnClickListener(listener);
        //endregion
        //region service mới

        //endregion
                return v;
    }
}
