package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;


import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IResultView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswer;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswers;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ResultPresenter implements IResultPresenter {

    /**
     * Storage current answer list
     */
    private MyAnswer[] myAnswers;

    /**
     * Storage current question
     */
    private Question[] questions;

    /**
     * The current view
     */
    private IResultView resultView;
    private AppCompatActivity GetView(){return (AppCompatActivity)resultView;}

    /**
     * Inject the arena service
     */
    @Inject
    IArenaService arenaService;

    /**
     * Inject the player service
     */
    @Inject
    IPlayerService playerService;

    /**
     * Inject the volley service
     */
    @Inject
    IVolleyService volleyService;

    public ResultPresenter(final IResultView theView)
    {
        //Set view
        this.resultView = theView;

        //Inject service
        ((SillyApp) ((AppCompatActivity) resultView).getApplication())
                .getDependencyComponent()
                .inject(this);

        GetAnswerResult();

        //Set button state.
        theView.SetButtonState(arenaService.CalledBattleFrom());
    }

    private void GetAnswerResult()
    {
        if(arenaService == null
            || playerService == null
            || playerService.GetCurrentUser() == null
            || arenaService.GetCurrentQuestions() == null)
            return;

        questions = arenaService.GetCurrentQuestions();
        if(questions.length != 5) return;

        arenaService.GetBattleResult(
                playerService.GetCurrentUser().getUserId(),
                questions[0].getBattleId(),
                GetView(),
                volleyService,
                new IVolleyResponse<MyAnswer[]>() {
                    @Override
                    public void onSuccess(MyAnswer[] answers) {
                        myAnswers = answers;
                        InitializeResultView(myAnswers);
                    }

                    @Override
                    public void onError(ErrorCode error) {
                    }
                });
    }

    //Initialize result view
    private boolean initialized = false;
    private void InitializeResultView(MyAnswer[] answers)
    {
        if(answers.length != 5 || questions.length != 5)
            return;

        for(int i = 0; i < questions.length; i++)
        {
            for(int j = 0; j < myAnswers.length; j++)
            {
                if(questions[i].getQuestionId() == myAnswers[j].getQuestionId())
                {
                    questions[i].setMyAnswer(myAnswers[j]);
                    break;
                }
            }
        }

        boolean[] answerStates = new boolean[5];

        for(int i = 0; i < questions.length; i++)
        {
            Common.AnswerKey myAnswer = questions[i].getMyAnswer().getAnswer();
            Common.AnswerKey trueAnswer = questions[i].getMyAnswer().getTrueAnswer();
            if( myAnswer == trueAnswer )
            {
                answerStates[i] = true;
            }
            else
            {
                answerStates[i] = false;
            }
        }
        initialized = true;
        resultView.SetAnswerArrayButton(answerStates);
        ShowQuestionWithIndex(0);
    }

    @Override
    public void ShowQuestionWithIndex(int index) {
        if(!initialized || index < 0 || index > 4) return;
        Question q = questions[index];
        resultView.SetQuestionTitle("This is question title!");
        resultView.SetQuestionContent(q.getQuestionContent());
        resultView.SetAnswerA(q.getAnswerA());
        resultView.SetAnswerB(q.getAnswerB());
        resultView.SetTotalTimes(223541);

        switch (q.getQuestionType())
        {
            case LISTENING:
                resultView.HideOrShowHearIcon(true);
                break;
            case READING:
                resultView.HideOrShowHearIcon(false);
                break;
            case NOT_FOUND:
                resultView.HideOrShowHearIcon(false);
                break;
        }

        resultView.HighlineTrueAnswer(q.getMyAnswer().getTrueAnswer());
    }

    /**
     * Get battle called from.
     * @return
     */
    public Common.BattleCalledFrom BattleCalledFrom()
    {
        return arenaService.CalledBattleFrom();
    }
}
