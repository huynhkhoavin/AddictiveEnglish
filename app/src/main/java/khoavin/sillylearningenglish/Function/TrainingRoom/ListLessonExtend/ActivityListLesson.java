package khoavin.sillylearningenglish.Function.TrainingRoom.ListLessonExtend;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter.SingleViewAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonDetailActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.RecyclerItemClickListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_MORE_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_POPULAR_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_RATING_LESSON;

/**
 * Created by KhoaVin on 29/06/2017.
 */

public class ActivityListLesson extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    int listType=0;
    ListLessonAdapter singleViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_list_lesson);

        ButterKnife.bind(this);

        listType = (int)Storage.getInstance().getValue(CURRENT_MORE_LESSON);

        setUpAdapter();

    }
    public void setUpAdapter(){

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        getListLesson(listType);
    }
    public void getListLesson(final int i){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                final Lesson[] lessons = JsonConvert.getArray(response,Lesson[].class);
                singleViewAdapter = new ListLessonAdapter(ActivityListLesson.this, ArrayConvert.toObjectArray(ArrayConvert.toObjectArray(lessons)));

                singleViewAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
                    @Override
                    public void OnClick(int ItemPosition, Object ItemObject) {
                        //Storage.getInstance().addValue(CURRENT_LESSON, adapter.getItem(Row,Column));
                        Storage.getInstance().addValue(CURRENT_LESSON,singleViewAdapter.getDataSource().get(ItemPosition));
                        Intent it = new Intent(ActivityListLesson.this, LessonDetailActivity.class);
                        startActivity(it);
                    }
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityListLesson.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(singleViewAdapter);
                recyclerView.setNestedScrollingEnabled(false);
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("limit_amount","0");
                return params;
            }

            @Override
            public String getAPI_URL() {
                if (i == 0) {
                    toolbar.setTitle("TOP POPULAR LESSON");
                    return GET_POPULAR_LESSON;
                }else
                {
                    toolbar.setTitle("TOP RATING LESSON");
                    return GET_RATING_LESSON;
                }
            }
        };
        networkAsyncTask.execute();
    }
}

