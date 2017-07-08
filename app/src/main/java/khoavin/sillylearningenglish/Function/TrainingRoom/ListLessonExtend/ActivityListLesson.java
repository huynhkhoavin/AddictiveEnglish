package khoavin.sillylearningenglish.Function.TrainingRoom.ListLessonExtend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.LessonInfo.LessonDetailActivity;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_MORE_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_POPULAR_LESSON;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_RATING_LESSON;

/**
 * Created by KhoaVin on 29/06/2017.
 */

public class ActivityListLesson extends BaseDetailActivity {

    @BindView(R.id.tvListType)
    TextView tvListType;
    @BindView(R.id.btnBack)
    ImageView btnBack;

    @BindView(R.id.titleBar)
    LinearLayout titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    int listType=0;
    ListLessonAdapter singleViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_list_lesson);

        ButterKnife.bind(this);

        setupWindowAnimations();

        listType = (int)Storage.getInstance().getValue(CURRENT_MORE_LESSON);

        setUpAdapter();

    }
    public void setUpAdapter(){
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                    tvListType.setText("TOP POPULAR LESSON");
                    return GET_POPULAR_LESSON;
                }else
                {
                    tvListType.setText("TOP RATING LESSON");
                    return GET_RATING_LESSON;
                }
            }
        };
        networkAsyncTask.execute();
    }
    //region transition
    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }
    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        // This view will not be affected by enter transition animation
        //enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        return enterTransition;
    }
    //endregion
}

