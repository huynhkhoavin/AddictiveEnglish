package khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.FUNCTION.Arena.Models.IAnswerModel;
import khoavin.sillylearningenglish.FUNCTION.Arena.Models.Implementation.AnswerModel;
import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.SERVICE.DIDagger.MyApp;
import khoavin.sillylearningenglish.SERVICE.Interfaces.IUserService;
import khoavin.sillylearningenglish.SERVICE.WebModels.User;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Question;
import rx.functions.Func1;

/**
 * Created by OatOal on 2/18/2017.
 */

public class AnswerPresenter implements IAnswerPresenter {

    //The Model and View
    private IAnswerView answerView;
    private IAnswerModel answerModel;

   @Inject
   IUserService mUserService;

    //The question list - value receive from answerModel
    private Question[] questions;

    public AnswerPresenter(final IAnswerView answerView) {
        this.answerView = answerView;
        this.answerModel = new AnswerModel();

        ((MyApp) ((AppCompatActivity) answerView).getApplication())
                .getNetComponent()
                .inject(this);

        //Test data --- this data must receive from server
        questions =  answerModel.GetQuestionList();
        if(questions != null && questions.length == 5)
        {
            Question data = questions[0];

            switch (data.getQuestionType())
            {
                case Listening:
                    this.answerView.ShowListeningIcon();
                    break;
                case Reading:
                    this.answerView.HideListeningIcon();
                    this.answerView.HideRepeatIcon();
                    break;
            }

            this.answerView.SetQuestionTitle(data.getQuestionTitle());
            this.answerView.SetQuestionContent(data.getQuestionContent());
            this.answerView.SetAnswerForQuestionA(data.getAnswerA());
            this.answerView.SetAnswerForQuestionB(data.getAnswerB());
            this.answerView.SetTimeProgressMaxValue(100);
            this.answerView.SetTimeProgressValue(50);

            if(mUserService != null)
            {
                mUserService.GetuserInformation(new Func1<User, Void>() {
                    @Override
                    public Void call(User user) {
                        Log.i("Test Dugger", "User name: " + user.getUserName());
                        Log.i("Test Dugger", "User Id: " +  user.getUserId());
                        Log.i("Test Dugger", "User Coin: " +  user.getCoins());
                        Log.i("Test Dugger", "User Avatar url: " +  user.getAvatarUrl());
                        return null;
                    }
                });
            }
            else
            {
                Log.e("Test Dugger", "Can not resolve IUserService!");
            }
        }
    }

    @Override
    public void ChoseAnswerA()
    {
        //Do something to select answer A
        Log.d("Chose answer A", "Answer Presenter");
    }

    @Override
    public void ChoseAnswerB() {
        //Do something to select answer B
        Log.d("Chose answer B", "Answer Presenter");
    }

    @Override
    public void RepeatAudio() {
        //Do something to repeat audio
        answerView.ShowListeningIcon();
        Log.d("Repeat audio", "Answer Presenter");
    }
}
