package khoavin.sillylearningenglish.FUNCTION.Arena.ArenaTools;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import khoavin.sillylearningenglish.ENTITY_DATABASE.*;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/14/2017.
 */

public class QuestionPageController {

    //region Controls

    private TextView titleTextView;
    private TextView contentTextView;
    private TextView answerATextView;
    private TextView answerBTextView;
    private ImageView currentHearImage;
    private ImageView repeatAudioImage;
    private int trueAnswerColor;
    private int defaultColor;

    //endregion

    //region Controls Models
    private Commom.QuestionType questionType;
    private boolean showAnswer;
    private boolean initYet = false;
    private String questionTitle, questionContent, answerA, answerB;

    //endregion

    //region Private Method

    //Init style after set controls value
    private void InitStyle()
    {
        this.answerATextView.setTextColor(defaultColor);
        this.answerBTextView.setTextColor(defaultColor);

        //Set icon
        switch (questionType)
        {
            case Listening:
                showListeningImage();
                break;
            case Reading:
                this.repeatAudioImage.setVisibility(View.GONE);
                this.currentHearImage.setVisibility(View.GONE);
                break;
        }

        //Set text
        this.titleTextView.setText(this.questionTitle);
        this.contentTextView.setText(this.questionContent);
        this.answerATextView.setText(this.answerA);
        this.answerBTextView.setText(answerB);
    }

    //endregion

    //region Constructor and Public Method

    //Init all controls
    public QuestionPageController(AppCompatActivity context)
    {
        this.titleTextView = (TextView)context.findViewById(R.id.question_title);
        this.contentTextView = (TextView) context.findViewById(R.id.question);
        this.answerATextView = (TextView)context.findViewById(R.id.answer_a);
        this.answerBTextView = (TextView)context.findViewById(R.id.answer_b);
        this.currentHearImage = (ImageView) context.findViewById(R.id.icon_playing);
        this.repeatAudioImage = (ImageView) context.findViewById(R.id.icon_repeat);
        this.trueAnswerColor = context.getResources().getColor(R.color.Green);
        this.defaultColor = context.getResources().getColor(R.color.BlackText);
    }

    //Set value for controls
    public void setValue(Commom.QuestionType questionType, String answerA, String answerB, String questionTitle, String questionContent) {
        this.questionType = questionType;
        this.answerB = answerB;
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.answerA = answerA;

        InitStyle();
    }

    public void showAnswer(Commom.AnswerKey answer)
    {
        switch (answer)
        {
            case A:
                this.answerATextView.setTextColor(trueAnswerColor);
                break;
            case B:
                this.answerBTextView.setTextColor(trueAnswerColor);
                break;
            case C:
                break;
            case D:
                break;
        }
    }

    public void showListeningImage()
    {
        if(questionType == Commom.QuestionType.Listening)
        {
            this.repeatAudioImage.setVisibility(View.GONE);
            this.currentHearImage.setVisibility(View.VISIBLE);
        }
    }

    public void showRepeatImage()
    {
        if(questionType == Commom.QuestionType.Listening)
        {
            this.repeatAudioImage.setVisibility(View.VISIBLE);
            this.currentHearImage.setVisibility(View.GONE);
        }
    }

    //endregion

}
