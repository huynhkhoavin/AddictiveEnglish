package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.Reading;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by Khoavin on 4/13/2017.
 */

public class FragmentA extends FragmentPattern {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_a,container,false);
        ButterKnife.bind(this,v);
        return v;
    }
}
