package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Function.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;

public class AnswerPresenter implements IAnswerPresenter {

    private static final String ANSWER_PRESENTER_TAG  = "ANSWER_PRESENTER: ";

    //The Model and View
    private IAnswerView answerView;

    private Questions questions;

    //Save current question on list question
    private int currentQuestion = 0;

    @Inject
    IArenaService arenaService;

    @Inject
    IPlayerService userService;

    public AnswerPresenter(final IAnswerView answerView) {

        //set view for presenter
        this.answerView = answerView;

        //inject arena service
        ((SillyApp) ((AppCompatActivity) answerView).getApplication())
                .getNetworkComponent()
                .inject(this);

        //Initialize questions
        questions = arenaService.GetCurrentQuestions();
        currentQuestion = 0;
        SetAnswerViewWithQuestion(questions.getData().get(currentQuestion));
    }

    @Override
    public void ChoseAnswerA() {
        //Do something to select answer A
        Log.d("Chose answer A", "Answer Presenter");
        currentQuestion--;
        if (currentQuestion >= 0 && currentQuestion < questions.getData().size())
            SetAnswerViewWithQuestion(questions.getData().get(currentQuestion));
        else {
            currentQuestion++;
        }
    }

    @Override
    public void ChoseAnswerB() {
        //Do something to select answer B
        Log.d("Chose answer B", "Answer Presenter");
        currentQuestion++;
        if (currentQuestion >= 0 && currentQuestion < questions.getData().size())
            SetAnswerViewWithQuestion(questions.getData().get(currentQuestion));
        else {
            currentQuestion--;
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

    private void GetNextQuestion()
    {
        currentQuestion++;
        if(currentQuestion < questions.getData().size())
        {
            this.SetAnswerViewWithQuestion(questions.getData().get(currentQuestion));
        }
        else
        {
            Log.i(ANSWER_PRESENTER_TAG, "End battle - Move to battle result!");
        }
    }
}