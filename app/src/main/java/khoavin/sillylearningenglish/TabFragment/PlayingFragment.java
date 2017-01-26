package khoavin.sillylearningenglish.TabFragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 1/26/2017.
 */

public class PlayingFragment extends TabFragment {
    MediaPlayer mPlayer;
    Button buttonPlay;
    Button buttonStop;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.playing_fragment,container,false);
        final String url = "http://192.168.1.102:8080/englishproject/listen/01.mp3";
        mPlayer = MediaPlayer.create(getContext(), Uri.parse(url));
        mPlayer.start();

        return v;
    }
}
