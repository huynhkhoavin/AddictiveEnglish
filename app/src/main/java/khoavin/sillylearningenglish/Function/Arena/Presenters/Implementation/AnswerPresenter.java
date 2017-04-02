package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.NetworkError;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Function.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;

import static khoavin.sillylearningenglish.SingleViewObject.Common.QuestionType.Listening;
import static khoavin.sillylearningenglish.SingleViewObject.Common.QuestionType.Reading;

public class AnswerPresenter implements IAnswerPresenter {

    //The Model and View
    private IAnswerView answerView;

    private Questions battleInformation;

    //Save current question on list question
    private int currentAnswer = 0;

    private static final String ANSWER_TAG = "ANSWER PRESENTER: ";

//    @Inject
//    IArenaService arenaService;

    @Inject
    IPlayerService userService;

    public AnswerPresenter(final IAnswerView answerView) {

        //set view for presenter
        this.answerView = answerView;

        //inject arena service
        ((SillyApp) ((AppCompatActivity) answerView).getApplication())
                .getNetworkComponent()
                .inject(this);

        //Get battle information and Init answer view
        GetBattleInformation("user_id", "enemy_id");
    }

    @Override
    public void ChoseAnswerA() {
        //Do something to select answer A
        Log.d("Chose answer A", "Answer TrainingPresenter");
        currentAnswer--;
        if (currentAnswer >= 0 && currentAnswer < battleInformation.getData().size())
            SetAnswerViewWithQuestion(battleInformation.getData().get(currentAnswer));
        else {
            currentAnswer++;
        }
    }

    @Override
    public void ChoseAnswerB() {
        //Do something to select answer B
        Log.d("Chose answer B", "Answer TrainingPresenter");
        currentAnswer++;
        if (currentAnswer >= 0 && currentAnswer < battleInformation.getData().size())
            SetAnswerViewWithQuestion(battleInformation.getData().get(currentAnswer));
        else {
            currentAnswer--;
        }
    }

    @Override
    public void RepeatAudio() {
        //Do something to repeat audio
        answerView.ShowListeningIcon();
        Log.d("Repeat audio", "Answer TrainingPresenter");
    }

    //Set answer view with question
    private void SetAnswerViewWithQuestion(Question question) {
        switch (question.getQuestionType()) {
            case Listening:
                this.answerView.ShowListeningIcon();
                break;
            case Reading:
                this.answerView.HideListeningIcon();
                this.answerView.HideRepeatIcon();
                break;
        }

        this.answerView.SetQuestionTitle("this is question's title!");
        this.answerView.SetQuestionContent(question.getQuestionContent());
        this.answerView.SetAnswerForQuestionA(question.getAnswerA());
        this.answerView.SetAnswerForQuestionB(question.getAnswerB());
        this.answerView.SetTimeProgressMaxValue(100);
        this.answerView.SetTimeProgressValue(50);
    }

    //Get battle information
    private void GetBattleInformation(String userId, String enemyId) {
        if (userService != null) {
            userService.GetuserInformation("b1d7dd8f11b32c9a0f66ea3c4416ca7f0aa02c80", new IServerResponse<User>() {
                @Override
                public void onSuccess(User responseObj) {
                    Log.i(ANSWER_TAG, "user id: " + responseObj.getUserId());
                    Log.i(ANSWER_TAG, "user name: " + responseObj.getName());
                    Log.i(ANSWER_TAG, "user coin: " + responseObj.getCoin());
                    Log.i(ANSWER_TAG, "user total match: " + responseObj.getTotalMatch());
                    Log.i(ANSWER_TAG, "user win match: " + responseObj.getWinMatch());
                    Log.i(ANSWER_TAG, "user avatar: " + responseObj.getAvatarUrl());
                    Log.i(ANSWER_TAG, "user rank: " + responseObj.getRank());
                    Log.i(ANSWER_TAG, "user level: " + responseObj.getLevel());
                }

                @Override
                public void onError(SillyError sillyError) {

                    Log.e(ANSWER_TAG, "Error code: " + sillyError.getErrorCode());
                    Log.e(ANSWER_TAG, "Error Message: " + sillyError.getMessage());

                }
            });
        }
    }
}
