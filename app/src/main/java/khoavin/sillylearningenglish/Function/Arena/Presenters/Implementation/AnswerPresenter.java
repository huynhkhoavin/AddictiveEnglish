package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.SingleViewObject.Common;

public class AnswerPresenter implements IAnswerPresenter {

    private static final String ANSWER_PRESENTER_TAG  = "ANSWER_PRESENTER: ";

    //The Model and View
    private IAnswerView answerView;
    private AppCompatActivity GetView(){return (AppCompatActivity)answerView;}

    private Question[] questions;

    //Save current question on list question
    private int currentQuestion = 0;

    @Inject
    IArenaService arenaService;

    @Inject
    IPlayerService userService;

    @Inject
    IVolleyService volleyService;

    public AnswerPresenter(final IAnswerView answerView) {

        //set view for presenter
        this.answerView = answerView;

        //inject arena service
        ((SillyApp) ((AppCompatActivity) answerView).getApplication())
                .getDependencyComponent()
                .inject(this);

        //Initialize questions
        questions = arenaService.GetCurrentQuestions();
        currentQuestion = 0;
        if(questions != null && questions.length > 0)
            SetAnswerViewWithQuestion(questions[currentQuestion]);
    }

    @Override
    public void ChoseAnswerA() {
        Question q = questions[currentQuestion];
        ChoseAnswer(q, Common.AnswerKey.A);
    }

    @Override
    public void ChoseAnswerB() {
        Question q = questions[currentQuestion];
        ChoseAnswer(q, Common.AnswerKey.B);
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
            case LISTENING:
                this.answerView.ShowListeningIcon();
                break;
            case READING:
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

    private void GetNextQuestion()
    {
        currentQuestion++;
        if(currentQuestion < questions.length)
        {
            this.SetAnswerViewWithQuestion(questions[currentQuestion]);
        }
        else
        {
            Log.i(ANSWER_PRESENTER_TAG, "End battle - Move to battle result!");
        }
    }

    private void ChoseAnswer(Question question, Common.AnswerKey answerKey)
    {
        if(arenaService == null) return;
        int myAnswer = 5;
        switch (answerKey)
        {
            case A:
                myAnswer = 1;
                break;
            case B:
                myAnswer = 2;
                break;
            case C:
                myAnswer = 3;
                break;
            case D:
                myAnswer = 4;
                break;
        }

        arenaService.ChoseAnswer(
                userService.GetCurrentUser().getUserId(),
                question.getBattleId(),
                question.getQuestionId(),
                myAnswer,
                GetView(),
                volleyService,
                new IVolleyResponse<AnswerChecker>() {
                    @Override
                    public void onSuccess(AnswerChecker checker) {
                        if(checker.getCheckerTrueFalse())
                        {
                            //Do something with true answer
                            Log.i(ANSWER_PRESENTER_TAG, "TRUE TRUE TRUE TRUE TRUE!");
                            answerView.InformTrueAnswer();
                        }
                        else
                        {
                            //Do something with wrong answer
                            Log.i(ANSWER_PRESENTER_TAG, "FALSE FALSE FALSE FALSE!");
                            answerView.InformFalseAnswer();
                        }

                        currentQuestion++;
                        if(currentQuestion > 4)
                        {
                            //Do something to end battle
                            Log.i(ANSWER_PRESENTER_TAG, "Battle has end!");
                            answerView.MoveToBattleResult();
                        }
                        else
                        {
                            //Move next
                            SetAnswerViewWithQuestion(questions[currentQuestion]);
                        }
                    }

                    @Override
                    public void onError(ErrorCode error) {
                    }
                });
    }
}