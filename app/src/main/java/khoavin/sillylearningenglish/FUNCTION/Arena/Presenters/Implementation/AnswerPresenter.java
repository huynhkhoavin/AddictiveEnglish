package khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation;

import android.util.Log;

import khoavin.sillylearningenglish.FUNCTION.Arena.Models.IAnswerModel;
import khoavin.sillylearningenglish.FUNCTION.Arena.Models.Implementation.AnswerModel;
import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Question;

/**
 * Created by OatOal on 2/18/2017.
 */

public class AnswerPresenter implements IAnswerPresenter {

    //The Model and View
    private IAnswerView answerView;
    private IAnswerModel answerModel;

    //The question list - value receive from answerModel
    private Question[] questions;

    public AnswerPresenter(final IAnswerView answerView) {
        this.answerView = answerView;
        this.answerModel = new AnswerModel();

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
