package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.Adapter.ExpandableAdapter;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/15/2017.
 */

public class TrainingCategoryFragment extends FragmentPattern {
    private ExpandableListView eplStudent;
    private HashMap<String, List<String>> mData;
    final List<String>listHeader = new ArrayList<>();
    private static final String TAG = "Training Category Fragment";
    public void FakeData(){
        listHeader.clear();
        listHeader.add("Podcast In English");
        listHeader.add("Oxford Bookworm");
        listHeader.add("BBC");

        mData = new HashMap<>();
        List<String> listSourcePodcast = new ArrayList<>();
        listSourcePodcast.add("Level 1");
        listSourcePodcast.add("Level 2");
        listSourcePodcast.add("Level 3");


        List<String> listOxfordBookworm = new ArrayList<>();
        listOxfordBookworm.add("Stage 1");
        listOxfordBookworm.add("Stage 2");

        List<String> listBBC = new ArrayList<>();
        listBBC.add("6 Minutes for English");
        listBBC.add("The English We Speak");
        listBBC.add("The Drama");
        listBBC.add("Stand Up Comedy");
        mData.put(listHeader.get(0), listSourcePodcast);
        mData.put(listHeader.get(1), listOxfordBookworm);
        mData.put(listHeader.get(2), listBBC);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_category_training,container,false);

        eplStudent = (ExpandableListView) v.findViewById(R.id.eplChat);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        eplStudent.setIndicatorBounds(width - dp2px(50), width - dp2px(10));

        FakeData();

        ExpandableAdapter expandableAdapter = new ExpandableAdapter(getContext(),listHeader,mData);
        eplStudent.setAdapter(expandableAdapter);
        eplStudent.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(),String.valueOf(childPosition),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return v;
    }
    public int dp2px(float dp) {
        // Get the screen's density scale
        final float density = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * density + 0.5f);
    }
}
