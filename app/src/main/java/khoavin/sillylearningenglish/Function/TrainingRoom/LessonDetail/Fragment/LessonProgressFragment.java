package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Object.LessonProgress;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.ILessonDetailView;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.ProgressListAdapter;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonTracker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.Pattern.ViewPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.*;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.*;
import static khoavin.sillylearningenglish.SYSTEM.Service.Constants.ACTION.UPDATE_PROGRESS_SUCCESS;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonProgressFragment extends FragmentPattern implements ILessonDetailView {

    ViewPager parentViewPager;
    @BindView(R.id.recyclerView) RecyclerView recycleView;
    @BindView(R.id.lesson_image)
    ImageView lessonAvatar;
    private ProgressListAdapter adapter;
    @Inject
    ITrainingService trainingService;
    @Inject
    IVolleyService volleyService;
    Lesson lesson;
    @Inject
    IAuthenticationService authenticationService;

    ArrayList<LessonUnit> lessonUnits;

    public void setParentViewPager(ViewPager parentViewPager) {
        this.parentViewPager = parentViewPager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lesson_detail_progress,container,false);
        ButterKnife.bind(this,v);
        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);
        setUpAdapter();
        getLessonUnit();
        EventBus.getDefault().register(this);
        showLessonInfo();
        return v;
    }
    public void showLessonInfo(){
        lesson = (Lesson)Storage.getValue(CURRENT_LESSON);
        Glide.with(getContext())
                .load(lesson.getLsAvatarUrl())
                .into(lessonAvatar);
    }
    public void getLessonUnit(){
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(getContext()) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LESSON_UNIT,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                lessonUnits = ArrayConvert.toArrayList(JsonConvert.getArray(response,LessonUnit[].class));
                                getProgress(ArrayConvert.toArrayList(JsonConvert.getArray(response,LessonUnit[].class)));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("ls_id",((Lesson)Storage.getValue(CURRENT_LESSON)).getLsId());
                        return params;
                    }
                };
                queue.add(stringRequest);

            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };
        progressAsyncTask.execute();

    }
    public void getProgress(final ArrayList<LessonUnit> lessonUnits){
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(getContext()) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_LESSON_TRACKER,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                LessonTracker[] lessonTrackers = JsonConvert.getArray(response,LessonTracker[].class);
                                LessonProgress lessonProgress = new LessonProgress(lessonUnits,lessonTrackers[0]);
                                int progress = lessonProgress.getCurrentProgressUnitId();
                                for (LessonUnit lessonUnit :lessonUnits) {
                                    lessonUnit.setCurrentProgressUnit(progress);
                                }
                                ShowProgress(lessonUnits);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("ls_id",((Lesson)Storage.getValue(CURRENT_LESSON)).getLsId());
                        params.put("user_id",authenticationService.getCurrentUser().getUid());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };
        progressAsyncTask.execute();
    }
    @Override
    public void ShowProgress(ArrayList<LessonUnit> lessonUnits) {
        adapter.setDataSource(ArrayConvert.toObjectArray(lessonUnits));
    }
    public void setUpAdapter(){
        adapter = new ProgressListAdapter(getActivity(), new ArrayList<>());
        adapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {
                parentViewPager.setCurrentItem(0);
                LessonUnit lessonUnit = (LessonUnit) ItemObject;
                EventBus.getDefault().post(new MessageEvent(Constants.ACTION.ADD_URL,SERVER_URL + lessonUnit.getLuUrl()));
                Storage.getInstance().addValue(CURRENT_LESSON_UNIT,lessonUnit);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setNestedScrollingEnabled(false);
    }

    public void setCurrentProgress(int progress){
        for(LessonUnit lessonUnit : lessonUnits){
            lessonUnit.setCurrentProgressUnit(progress);
        }
        adapter.setDataSource(ArrayConvert.toObjectArray(lessonUnits));
    }
    @Subscribe
    public void onEvent(String Message){
        if (Message == UPDATE_PROGRESS_SUCCESS){
            getLessonUnit();
            //ShowProgress(lessonUnits);
        }
    }

    public String convert(long millis){
        String hms = String.format("%d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        //System.out.println(hms);
        return hms.substring(2);
    }
}
