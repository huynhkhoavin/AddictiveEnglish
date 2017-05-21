package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Presenter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.LessonDetailView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class LessonDetailPresenter {
    Activity activity;
    LessonDetailView lessonDetailView;
    @Inject
    ITrainingService trainingService;

    public LessonDetailPresenter(Activity activity) {
        this.activity = activity;
        lessonDetailView = new LessonDetailView(activity);
        //((SillyApp)(((AppCompatActivity)activity).getApplication())).getDependencyComponent().inject(this);
    }
    public void PlayLesson(){
    }
}
