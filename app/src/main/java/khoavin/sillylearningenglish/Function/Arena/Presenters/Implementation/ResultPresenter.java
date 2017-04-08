package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;


import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Function.Arena.Presenters.IResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IResultView;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
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

    private static final String RESULT_VIEW_TAG = "RESULT_VIEW: ";

    //The answer reuslts
    private MyAnswers myAnswers;

    private Questions questions;

    //The view
    private IResultView resultView;

    //The service
    @Inject
    IArenaService arenaService;

    @Inject
    IPlayerService playerService;

    public ResultPresenter(final IResultView theView)
    {
        //Set view
        this.resultView = theView;

        //Inject service
        ((SillyApp) ((AppCompatActivity) resultView).getApplication())
                .getDependencyComponent()
                .inject(this);

        GetAnswerResult();
    }

    private void GetAnswerResult()
    {
        if(arenaService == null
            || playerService == null
            || playerService.GetCurrentUser() == null
            || arenaService.GetCurrentQuestions() == null)
            return;

        questions = arenaService.GetCurrentQuestions();
        if(questions.getData().size() != 5) return;
        arenaService.GetBattleResult(
                playerService.GetCurrentUser().getUserId(),
                questions.getData().get(0).getBattleId(),
                new IServerResponse<MyAnswers>() {
                    @Override
                    public void onSuccess(MyAnswers answers) {
                        myAnswers = answers;
                        InitializeResultView(myAnswers);
                    }

                    @Override
                    public void onError(SillyError error) {
                        //Error
                    }
                });
    }

    //Initialize result view
    private boolean initialized = false;
    private void InitializeResultView(MyAnswers answers)
    {
        if(answers.getData().size() != 5 || questions.getData().size() != 5)
            return;

        for(int i = 0; i < questions.getData().size(); i++)
        {
            for(int j = 0; j < myAnswers.getData().size(); j++)
            {
                if(questions.getData().get(i).getQuestionId() == myAnswers.getData().get(j).getQuestionId())
                {
                    questions.getData().get(i).setMyAnswer(myAnswers.getData().get(j));
                    break;
                }
            }
        }

        boolean[] answerStates = new boolean[5];

        for(int i = 0; i < questions.getData().size(); i++)
        {
            Common.AnswerKey myAnswer = questions.getData().get(i).getMyAnswer().getAnswer();
            Common.AnswerKey trueAnswer = questions.getData().get(i).getMyAnswer().getTrueAnswer();
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
        Question q = questions.getData().get(index);
        resultView.SetQuestionTitle("This is question title!");
        resultView.SetQuestionContent(q.getQuestionContent());
        resultView.SetAnswerA(q.getAnswerA());
        resultView.SetAnswerB(q.getAnswerB());
        resultView.SetTotalTimes(223541);

        switch (q.getQuestionType())
        {
            case Listening:
                resultView.HideOrShowHearIcon(true);
                break;
            case Reading:
                resultView.HideOrShowHearIcon(false);
                break;
            case Unknow:
                resultView.HideOrShowHearIcon(false);
                break;
        }

        resultView.HighlineTrueAnswer(q.getMyAnswer().getTrueAnswer());
    }
}
