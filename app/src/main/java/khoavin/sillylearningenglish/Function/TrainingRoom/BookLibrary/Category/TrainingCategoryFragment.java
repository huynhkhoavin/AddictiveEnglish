package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.Adapter.ExpandableAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.ListLessonExtend.ActivityListLesson;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonSource;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.SourceUnit;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static com.facebook.FacebookSdk.getApplicationContext;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_CATEGORY;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_ALL_SOURCE;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_ALL_SOURCE_UNIT;

/**
 * Created by KhoaVin on 2/15/2017.
 */

public class TrainingCategoryFragment extends FragmentPattern {

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    ArrayList<String> expandableListTitle = new ArrayList<>();
    HashMap<String, ArrayList<String>> expandableListDetail = new HashMap<>();
    HashMap<String, ArrayList<String>> expandableListDetailTitle = new HashMap<>();
    HashMap<LessonSource,ArrayList<SourceUnit>> sourceStorage = new HashMap<>();
    ArrayList<LessonSource> listSource = new ArrayList<>();
    ArrayList<SourceUnit>listSourceUnit = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_training,container,false);
        ButterKnife.bind(this,v);

        getListSource();

        return v;
    }
    public void setupExpandableListView(HashMap<String, ArrayList<String>> hash){
        //expandableListDetail = hash;

        for (int i = expandableListDetail.size()-1; i>=0;i--){
            String sourceid = listSource.get(i).getSourceId();
            expandableListDetailTitle.put(listSource.get(i).getSourceName(),expandableListDetail.get(listSource.get(i).getSourceId()));
        }
        expandableListTitle = new ArrayList<String>();
        for (int i = 0; i< listSource.size();i++){
            expandableListTitle.add(listSource.get(i).getSourceName());
        }
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetailTitle);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT);
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT);

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                ArrayList<SourceUnit> list = sourceStorage.get(listSource.get(groupPosition));

                Toast.makeText(getContext(),list.get(childPosition).getLsuName(),Toast.LENGTH_SHORT);

                Storage.getInstance().addValue(CURRENT_CATEGORY,list.get(childPosition));

                Intent it = new Intent(getActivity(), ActivityListCategory.class);
                startActivity(it);
                return false;
            }
        });

    }
    public void getListSource(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                listSource = ArrayConvert.toArrayList(JsonConvert.getArray(response,LessonSource[].class));
                for (int i = listSource.size()-1; i>=0;i--){
                    expandableListDetail.put(listSource.get(i).getSourceId(),new ArrayList<String>());
                    sourceStorage.put(listSource.get(i),new ArrayList<SourceUnit>());
                }
                getSourceUnit();
            }

            @Override
            public Map<String, String> getListParams() {
                return null;
            }

            @Override
            public String getAPI_URL() {
                return GET_ALL_SOURCE;
            }
        };
        networkAsyncTask.execute();
    }
    public void getSourceUnit(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                listSourceUnit = ArrayConvert.toArrayList(JsonConvert.getArray(response,SourceUnit[].class));
                for (int i = 0; i<listSourceUnit.size();i++){
                    expandableListDetail.get(listSourceUnit.get(i).getLsuSource()).add(listSourceUnit.get(i).getLsuName());
                    int sourceId = Integer.parseInt(listSourceUnit.get(i).getLsuSource());
                    sourceStorage.get(getSourceFromId(sourceId)).add(listSourceUnit.get(i));
                }
                setupExpandableListView(expandableListDetail);
            }

            @Override
            public Map<String, String> getListParams() {
                return null;
            }

            @Override
            public String getAPI_URL() {
                return GET_ALL_SOURCE_UNIT;
            }
        };
        networkAsyncTask.execute();
    }
    public LessonSource getSourceFromId(int id){
        for (int i = 0; i < listSource.size();i++){
            if (listSource.get(i).getSourceId().equals(String.valueOf(id))){
                return listSource.get(i);
            }
        }
        return null;
    }
}
