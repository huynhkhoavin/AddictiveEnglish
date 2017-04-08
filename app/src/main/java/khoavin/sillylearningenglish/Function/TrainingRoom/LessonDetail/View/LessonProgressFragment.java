package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Presenter.LessonDetailPresenter;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.ProgressUnit;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonProgressFragment extends FragmentPattern implements ILessonDetailView {
    private LessonDetailPresenter lessonDetailPresenter;
    @BindView(R.id.recyclerView) RecyclerView recycleView;
    private ProgressListAdapter adapter;


    private void initComponent(View v){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lesson_detail_progress,container,false);
        ButterKnife.bind(this,v);
        initComponent(v);
        ArrayList<ProgressUnit> progressUnitArrayList = new ArrayList<>();
        progressUnitArrayList.add(new ProgressUnit(0,"Lesson 1","32:00",false));
        progressUnitArrayList.add(new ProgressUnit(1,"Lesson 1","32:00",false));
        progressUnitArrayList.add(new ProgressUnit(1,"Lesson 1","32:00",false));
        progressUnitArrayList.add(new ProgressUnit(1,"Lesson 1","32:00",false));
        progressUnitArrayList.add(new ProgressUnit(1,"Lesson 1","32:00",false));

        ShowProgress(progressUnitArrayList);

        return v;
    }

    @Override
    public void ShowProgress(ArrayList<ProgressUnit> progressUnits) {
        adapter = new ProgressListAdapter(getContext(), ArrayConvert.toObjectArray(progressUnits));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        recycleView.addItemDecoration(dividerItemDecoration);
    }
}
