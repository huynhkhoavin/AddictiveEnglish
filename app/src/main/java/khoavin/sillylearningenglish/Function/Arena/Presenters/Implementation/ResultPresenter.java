package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;


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
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress;
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
    private BaseDetailActivity GetView(){return (BaseDetailActivity)resultView;}

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

    /**
     * The current showed answer
     */
    private int currentShowedAnswer;

    public ResultPresenter(final IResultView theView)
    {
        //Set view
        this.resultView = theView;

        //Inject service
        ((SillyApp) ((AppCompatActivity) resultView).getApplication())
                .getDependencyComponent()
                .inject(this);

        currentShowedAnswer = -10;

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
        if(!initialized || index < 0 || index > 4 || currentShowedAnswer == index) return;

        Question q = questions[index];
        resultView.SetQuestionTitle(String.format(GetView().getResources().getString(R.string.answer_number), String.valueOf(index + 1)));
        resultView.SetQuestionContent(q.getQuestionContent());
        resultView.SetAnswerA(q.getAnswerA());
        resultView.SetAnswerB(q.getAnswerB());
        //resultView.SetTotalTimes(223541);
        resultView.setQuestionType(q.getQuestionType());
        resultView.HeightLineTrueAnswer(q.getMyAnswer().getTrueAnswer());
        resultView.HighlighSelectedAnswer(index);
        if(q.getQuestionType() == Common.QuestionType.LISTENING)
        {
            resultView.SetMediaUrl(WebAddress.SERVER_URL +  q.getAudioSource());
        }
        else
        {
            resultView.StopMedia();
        }

        currentShowedAnswer = index;
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
