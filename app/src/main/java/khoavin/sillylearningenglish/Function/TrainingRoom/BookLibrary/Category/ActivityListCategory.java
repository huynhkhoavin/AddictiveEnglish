package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonDetailActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.ListLessonExtend.ActivityListLesson;
import khoavin.sillylearningenglish.Function.TrainingRoom.ListLessonExtend.ListLessonAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.SourceUnit;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_CATEGORY;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_MORE_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_LESSON_BY_CATEGORY;

/**
 * Created by KhoaVin on 06/07/2017.
 */

public class ActivityListCategory extends AppCompatActivity {

    @BindView(R.id.tvListType)
    TextView tvTitle;

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    SourceUnit sourceUnit;
    ListLessonAdapter singleViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_list_lesson);

        ButterKnife.bind(this);

        sourceUnit = (SourceUnit) Storage.getInstance().getValue(CURRENT_CATEGORY);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText(sourceUnit.getLsuName());
        setUpAdapter();
        getLessonByCategory();

    }
    public void setUpAdapter(){
        singleViewAdapter = new ListLessonAdapter(this,new ArrayList<>());
        singleViewAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {
                //Storage.getInstance().addValue(CURRENT_LESSON, adapter.getItem(Row,Column));
                Storage.getInstance().addValue(CURRENT_LESSON,singleViewAdapter.getDataSource().get(ItemPosition));
                Intent it = new Intent(ActivityListCategory.this, LessonDetailActivity.class);
                startActivity(it);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityListCategory.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(singleViewAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
    public void getLessonByCategory(){
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(this) {
            @Override
            public void Response(String response) {
                ArrayList<Lesson> listLesson = ArrayConvert.toArrayList(JsonConvert.getArray(response,Lesson[].class));
                if (listLesson.size()==0){
                    Toast.makeText(ActivityListCategory.this,"No data to show!",Toast.LENGTH_SHORT).show();
                }
                singleViewAdapter.setDataSource(ArrayConvert.toObjectArray(listLesson));
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("source_unit",sourceUnit.getLsuId());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_LESSON_BY_CATEGORY;
            }
        };
        networkAsyncTask.execute();
    }
}