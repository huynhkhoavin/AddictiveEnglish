package khoavin.sillylearningenglish.Function.TrainingRoom.Home.Presenter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lessons;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;

/**
 * Created by Khoavin on 4/2/2017.
 */

public class TrainingPresenter implements ITrainingPresenter {
    Activity activity;

    @Inject
    ITrainingService trainingService;
    public TrainingPresenter(Activity controlActivity){

        this.activity = controlActivity;
        ((SillyApp)(((AppCompatActivity)activity).getApplication())).getNetworkComponent().inject(this);
    }
    public void TestFunction(){
        trainingService.GetPopularLesson(new IServerResponse<Lessons>() {
            @Override
            public void onSuccess(Lessons responseObj) {

            }

            @Override
            public void onError(SillyError sillyError) {

            }
        });
    }
}
