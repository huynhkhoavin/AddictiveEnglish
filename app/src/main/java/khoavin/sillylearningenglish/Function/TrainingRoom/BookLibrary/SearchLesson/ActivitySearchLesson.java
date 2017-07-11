package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.SearchLesson;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter.SingleViewAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonDetailActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.RecyclerItemClickListener;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_SEARCH_KEY;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_USER_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.SEARCH_LESSON;

/**
 * Created by KhoaVin on 11/07/2017.
 */

public class ActivitySearchLesson extends BaseDetailActivity {
    @BindView(R.id.swipeLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.titleBar)
    LinearLayout titleBar;


    public SingleViewAdapter singleViewAdapter;

    public String searchKey = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lesson);
        ((SillyApp) getApplication()).getDependencyComponent().inject(this);
        ButterKnife.bind(this);

        searchKey = (String)Storage.getInstance().getValue(CURRENT_SEARCH_KEY);

        setupAdapter();
        getUserLesson();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserLesson();
            }
        });
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void getUserLesson(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                Lesson[] lessons = JsonConvert.getArray(response,Lesson[].class);
                singleViewAdapter.setDataSource(ArrayConvert.toObjectArray(lessons));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("search_key",searchKey);
                return params;
            }

            @Override
            public String getAPI_URL() {
                return SEARCH_LESSON;
            }
        };
        networkAsyncTask.execute();
    }
    public void setupAdapter(){
        singleViewAdapter = new SingleViewAdapter(this,new ArrayList<>());
        singleViewAdapter.setOnItemClickListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Intent it = new Intent(getContext(), LessonInfoFragment.class);
                //To pass:
                Storage.getInstance().addValue(CURRENT_LESSON, singleViewAdapter.getDataSource().get(position));

                Intent it = new Intent(ActivitySearchLesson.this, LessonDetailActivity.class);
                startActivity(it);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(singleViewAdapter);
        recyclerView.setNestedScrollingEnabled(false);

    }
}
