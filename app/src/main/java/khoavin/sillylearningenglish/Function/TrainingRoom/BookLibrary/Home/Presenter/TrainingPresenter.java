package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Presenter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Listener.SortListener;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lessons;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by Khoavin on 4/2/2017.
 */

public class TrainingPresenter {
    Activity activity;

    @Inject
    ITrainingService trainingService;
    public TrainingPresenter(Activity controlActivity){

        this.activity = controlActivity;
        ((SillyApp)(((AppCompatActivity)activity).getApplication())).getDependencyComponent().inject(this);
    }
    public void TestFunction(){


    }

    public void GetPopularLesson(final SortListener sortListener) {
        trainingService.GetPopularLesson(new IServerResponse<Lessons>() {
            @Override
            public void onSuccess(Lessons responseObj) {
                sortListener.PopularSort(ArrayConvert.toArrayList(responseObj.getData()));
            }
            @Override
            public void onError(SillyError sillyError) {

            }
        });
    }
}
