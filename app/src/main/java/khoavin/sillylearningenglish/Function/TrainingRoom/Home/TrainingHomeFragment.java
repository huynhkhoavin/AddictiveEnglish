package khoavin.sillylearningenglish.Function.TrainingRoom.Home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Adapter.RecyclerViewDataAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Model.SectionDataModel;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Model.SingleItemModel;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/13/2017.
 */

public class TrainingHomeFragment extends FragmentPattern {


    ArrayList<SectionDataModel> allSampleData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_traning_room,container,false);
        allSampleData = new ArrayList<SectionDataModel>();
        createDummyData();
        RecyclerView my_recycler_view = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);
        return v;
    }
    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
}
