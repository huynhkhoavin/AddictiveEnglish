package khoavin.sillylearningenglish.Function.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Profile.Adapter.LessonProgressAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.ActivityListCategory;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonProgress;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_LESSON_PROGRESS;

/**
 * Created by KhoaVin on 10/07/2017.
 */

public class FragmentTrainingProfile extends FragmentPattern {
    @BindView(R.id.btnShowChart)
    ImageView btnShowChart;

    LessonProgressAdapter lessonProgressAdapter;

    @Inject
    IAuthenticationService authenticationService;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_training_profile,container,false);
        ButterKnife.bind(this,v);

        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);


        btnShowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),ActivityReport.class);
                startActivity(it);
            }
        });
        SetUpAdapter();
        getLessonProgress();
        return v;
    }
    public void SetUpAdapter(){
        lessonProgressAdapter = new LessonProgressAdapter(getContext(),new ArrayList<>());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(lessonProgressAdapter);
    }
    public void getLessonProgress(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                LessonProgress[] lessonProgresses = JsonConvert.getArray(response,LessonProgress[].class);

                lessonProgressAdapter.setDataSource(ArrayConvert.toObjectArray(lessonProgresses));
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",authenticationService.getCurrentUser().getUid());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_LESSON_PROGRESS;
            }
        };
        networkAsyncTask.execute();
    }
}
