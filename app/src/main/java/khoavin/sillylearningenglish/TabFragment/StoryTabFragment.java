package khoavin.sillylearningenglish.TabFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 1/23/2017.
 */

public class StoryTabFragment extends TabFragment implements ITabFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.listening_story_tab,container,false);
        return v;
    }
}
