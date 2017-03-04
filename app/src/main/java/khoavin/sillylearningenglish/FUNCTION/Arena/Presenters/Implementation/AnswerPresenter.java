package khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IUserService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleQuestions;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import rx.functions.Func1;

public class AnswerPresenter implements IAnswerPresenter {

    //The Model and View
    private IAnswerView answerView;

    private BattleQuestions battleInformation;

    //Save current question on list question
    private int currentAnswer = 0;

    private static final String ANSWER_TAG = "Answer service: ";

//    @Inject
//    IArenaService arenaService;

    @Inject
    IUserService userService;

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
        Log.d("Chose answer A", "Answer Presenter");
        currentAnswer--;
        if(currentAnswer >= 0 && currentAnswer < battleInformation.getData().size())
            SetAnswerViewWithQuestion(battleInformation.getData().get(currentAnswer));
        else
        {
            currentAnswer++;
        }
    }

    @Override
    public void ChoseAnswerB() {
        //Do something to select answer B
        Log.d("Chose answer B", "Answer Presenter");
        currentAnswer++;
        if(currentAnswer >= 0 && currentAnswer < battleInformation.getData().size())
            SetAnswerViewWithQuestion(battleInformation.getData().get(currentAnswer));
        else
        {
            currentAnswer--;
        }
    }

    @Override
    public void RepeatAudio() {
        //Do something to repeat audio
        answerView.ShowListeningIcon();
        Log.d("Repeat audio", "Answer Presenter");
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
    private void GetBattleInformation(String userId, String enemyId)
    {
        if (userService != null) {
//            arenaService.CreateBattle(userId, enemyId, new Func1<BattleQuestions, Void>() {
//                @Override
//                public Void call(BattleQuestions info) {
//                    if(info != null)
//                    {
//                        battleInformation = info;
//                        SetAnswerViewWithQuestion(battleInformation.getData().get(1));
//                    }
//                    else
//                    {
//                        Log.e("Answer presenter", "Can not create battle!");
//                    }
//                    return null;
//                }
//            });

            userService.GetuserInformation("b1d7dd8f11b32c9a0f66ea3c4416ca7f0aa02c80", new Func1<User, Void>() {
                @Override
                public Void call(User user) {
                    Log.i(ANSWER_TAG, "user id: " + "#########################");
                    if(user == null)
                    {
                        Log.i(ANSWER_TAG, "null cmnr");
                    }
                    else
                    {
                        Log.i(ANSWER_TAG, "user id: " + user.getUserId());
                        Log.i(ANSWER_TAG, "user name: " + user.getName());
                        Log.i(ANSWER_TAG, "user coin: " + user.getCoin());
                        Log.i(ANSWER_TAG, "user total match: " + user.getTotalMatch());
                        Log.i(ANSWER_TAG, "user win match: " + user.getWinMatch());
                        Log.i(ANSWER_TAG, "user avatar: " + user.getAvatarUrl());
                        Log.i(ANSWER_TAG, "user rank: " + user.getRank());
                        Log.i(ANSWER_TAG, "user level: " + user.getLevel());
                    }

                    Log.i(ANSWER_TAG, "user id: " + "#########################");
                    return null;
                }
            });
        }
    }
}
