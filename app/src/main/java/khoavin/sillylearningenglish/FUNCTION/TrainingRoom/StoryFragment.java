package khoavin.sillylearningenglish.FUNCTION.TrainingRoom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.PATTERN.FragmentPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 1/23/2017.
 */

public class StoryFragment extends FragmentPattern {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.single_podcast,container,false);
        return v;
    }
}
