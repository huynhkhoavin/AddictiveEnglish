package khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.NetworkDepdency.Network.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleInformation;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import rx.functions.Func1;

public class AnswerPresenter implements IAnswerPresenter {

    //The Model and View
    private IAnswerView answerView;

    private BattleInformation battleInformation;

    //Save current question on list question
    private int currentAnswer = 0;

    @Inject
    IArenaService arenaService;

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
        if(currentAnswer >= 0 && currentAnswer < battleInformation.getQuestionList().size())
            SetAnswerViewWithQuestion(battleInformation.getQuestionList().get(currentAnswer));
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
        if(currentAnswer >= 0 && currentAnswer < battleInformation.getQuestionList().size())
            SetAnswerViewWithQuestion(battleInformation.getQuestionList().get(currentAnswer));
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
        switch (question.getQuestionEnumType()) {
            case Listening:
                this.answerView.ShowListeningIcon();
                break;
            case Reading:
                this.answerView.HideListeningIcon();
                this.answerView.HideRepeatIcon();
                break;
        }

        this.answerView.SetQuestionTitle(question.getQuestionTitle());
        this.answerView.SetQuestionContent(question.getQuestionContent());
        this.answerView.SetAnswerForQuestionA(question.getAnswerA());
        this.answerView.SetAnswerForQuestionB(question.getAnswerB());
        this.answerView.SetTimeProgressMaxValue(100);
        this.answerView.SetTimeProgressValue(50);
    }

    //Get battle information
    private void GetBattleInformation(String userId, String enemyId)
    {
        if (arenaService != null) {
            arenaService.CreateBattle(userId, enemyId, new Func1<BattleInformation, Void>() {
                @Override
                public Void call(BattleInformation info) {
                    if(info != null)
                    {
                        battleInformation = info;
                        SetAnswerViewWithQuestion(battleInformation.getQuestionList().get(1));
                    }
                    else
                    {
                        Log.e("Answer presenter", "Can not create battle!");
                    }
                    return null;
                }
            });
        }
    }
}
