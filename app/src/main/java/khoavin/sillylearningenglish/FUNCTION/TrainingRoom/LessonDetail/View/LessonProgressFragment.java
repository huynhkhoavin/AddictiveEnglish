package khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.View;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.Model.ILessonDetailModel;
import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.Model.LessonDetailModel;
import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.Presenter.ILessonDetailPresenter;
import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.Presenter.LessonDetailPresenter;
import khoavin.sillylearningenglish.PATTERN.FragmentPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.ProgressUnit;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonProgressFragment extends FragmentPattern implements ILessonDetailView {
    private ILessonDetailModel lessonDetailModel;
    private ILessonDetailPresenter lessonDetailPresenter;
    private RecyclerView recycleView;
    private ProgressListAdapter adapter;


    private void initComponent(View v){
        recycleView = (RecyclerView)v.findViewById(R.id.recyclerView);
        lessonDetailModel = new LessonDetailModel();
        lessonDetailPresenter = new LessonDetailPresenter(this,lessonDetailModel);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_lesson_detail_progress,container,false);

        initComponent(v);
        lessonDetailPresenter.ShowProgressList();
        return v;
    }

    @Override
    public void ShowProgress(ProgressUnit[] progressUnits) {
        adapter = new ProgressListAdapter(getContext(),progressUnits);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        recycleView.addItemDecoration(dividerItemDecoration);
    }
}
