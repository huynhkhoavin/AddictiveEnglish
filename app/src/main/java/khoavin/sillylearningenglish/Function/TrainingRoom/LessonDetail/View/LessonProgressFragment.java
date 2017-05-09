package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Presenter.LessonDetailPresenter;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnits;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.MessageEvent.MessageEvent;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;
import khoavin.sillylearningenglish.SingleViewObject.ProgressUnit;

import static khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils.BASE_URL;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonProgressFragment extends FragmentPattern implements ILessonDetailView {
    private LessonDetailPresenter lessonDetailPresenter;
    @BindView(R.id.recyclerView) RecyclerView recycleView;
    private ProgressListAdapter adapter;
    @Inject
    ITrainingService trainingService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lesson_detail_progress,container,false);
        ButterKnife.bind(this,v);
        ((SillyApp)(((AppCompatActivity)getActivity()).getApplication())).getDependencyComponent().inject(this);
        trainingService.GetLessonUnit(4, new IServerResponse<LessonUnits>() {
            @Override
            public void onSuccess(LessonUnits responseObj) {
                ArrayList<ProgressUnit> progressUnitArrayList = new ArrayList<>();
                for(LessonUnit ls : responseObj.getData()){
                    progressUnitArrayList.add(new ProgressUnit(0,ls.getLuName(),"32:00",false,ls.getLuUrl()));
                }
                ShowProgress(progressUnitArrayList);
            }

            @Override
            public void onError(SillyError sillyError) {

            }
        });


        return v;
    }

    public void getLessonUnit(){

    }
    @Override
    public void ShowProgress(ArrayList<ProgressUnit> progressUnits) {
        adapter = new ProgressListAdapter(getContext(), ArrayConvert.toObjectArray(progressUnits));
        adapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {
                ProgressUnit progressUnit = (ProgressUnit) ItemObject;
                EventBus.getDefault().post(new MessageEvent(Constants.ACTION.ADD_URL,BASE_URL + progressUnit.getUrl()));
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        recycleView.addItemDecoration(dividerItemDecoration);
    }
}
