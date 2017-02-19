package khoavin.sillylearningenglish.FUNCTION.Arena.Views.Implementation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Presenters.Implementation.AnswerPresenter;
import khoavin.sillylearningenglish.FUNCTION.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Common;

/**
 * Created by OatOal on 2/13/2017.
 */

public class AnswerActivity extends AppCompatActivity implements IAnswerView {

    //region XML view components

    TextView totalTime;
    ProgressBar totalTimeProgressBar;
    TextView questionTitle;
    TextView questionContent;
    TextView answerA;
    TextView answerB;
    ImageView hearImage;
    ImageView repeatImage;

    //endregion

    //region Private properties

    private int defaultColor;
    private int trueAnswerColor;
    private long progressMaxValue;

    //endregion

    //region Presenter

    private IAnswerPresenter answerPresenter;

    //endregion

    //region Activity implementation

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        setTitle(R.string.answer_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        //Load all controls
        LoadAllControls();

        BindingEvent();

        //Init presenter
        this.answerPresenter = new AnswerPresenter(this);

    }

    //endregion

    //region IAnswerView implementation
    @Override
    public void SetTimeProgressMaxValue(long maxValue)
    {
        if(maxValue <= 0)
            this.progressMaxValue = 100;
        this.progressMaxValue = maxValue;
    }

    @Override
    public void SetTimeProgressValue(long value)
    {
        if(value < 0 || value > progressMaxValue)
        {
            value = 0;
        }

        this.totalTimeProgressBar.setProgress((int)(value / progressMaxValue) * 100);

        //Set time - Need refactor
        this.totalTime.setText(Float.toString(value));
    }

    @Override
    public void SetQuestionTitle(String questionTitle)
    {
        this.questionTitle.setText(questionTitle);
    }

    @Override
    public void SetQuestionContent(String questionContent)
    {
        this.questionContent.setText(questionContent);
    }

    @Override
    public void SetAnswerForQuestionA(String answerA)
    {
        this.answerA.setText(answerA);
    }

    @Override
    public void SetAnswerForQuestionB(String answerB)
    {
        this.answerB.setText(answerB);
    }

    @Override
    public void ShowListeningIcon()
    {
        this.hearImage.setVisibility(View.VISIBLE);
        this.repeatImage.setVisibility(View.GONE);
    }

    @Override
    public void HideListeningIcon() {
        this.hearImage.setVisibility(View.GONE);
    }

    @Override
    public void ShowRepeatIcon()
    {
        this.hearImage.setVisibility(View.GONE);
        this.repeatImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void HideRepeatIcon()
    {
        this.repeatImage.setVisibility(View.GONE);
    }

    @Override
    public void HighlineTrueAnswer(Common.AnswerKey answerKey)
    {
        switch (answerKey)
        {
            case A:
                this.answerA.setTextColor(trueAnswerColor);
                this.answerB.setTextColor(defaultColor);
                break;
            case B:
                this.answerA.setTextColor(defaultColor);
                this.answerB.setTextColor(trueAnswerColor);
                break;
        }
    }

    private void BindingEvent()
    {
        ImageView choseA = (ImageView) findViewById(R.id.select_a_button);
        choseA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPresenter.ChoseAnswerA();
            }
        });

        ImageView choseB = (ImageView) findViewById(R.id.select_b_button);
        choseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPresenter.ChoseAnswerB();
            }
        });

        repeatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPresenter.RepeatAudio();
            }
        });
    }

    //endregion

    //region Private method

    //Load all controls
    private void LoadAllControls()
    {
        //General controls
        this.totalTime = (TextView) findViewById(R.id.total_time);
        this.questionTitle = (TextView) findViewById(R.id.question_title);
        this.questionContent = (TextView) findViewById(R.id.question);
        this.answerA = (TextView) findViewById(R.id.answer_a);
        this.answerB = (TextView) findViewById(R.id.answer_b);
        this.hearImage = (ImageView) findViewById(R.id.icon_playing);
        this.repeatImage= (ImageView) findViewById(R.id.icon_repeat);

        //The true answer color and default color
        this.trueAnswerColor = getResources().getColor(R.color.Green);
        this.defaultColor = getResources().getColor(R.color.BlackText);

        //The progress bar
        this.totalTimeProgressBar = (ProgressBar) findViewById(R.id.total_time_progressbar);
        Drawable draw= getResources().getDrawable(R.drawable.custom_progressbar);
        this.totalTimeProgressBar.setProgressDrawable(draw);
        this.totalTimeProgressBar.setProgress(25);
    }

    //On the battle, duration of listener question is short (less than 1h)
    long seconds, mins, hours;
    private String MillisecondToString(long milliSecond)
    {
        seconds = milliSecond / 1000;
        if(seconds < 60)
        {
            return "0:" + seconds;
        }
        else if(seconds > 60 && seconds < 3600)
        {
            mins = seconds / 60;
            seconds = seconds % 60;
            return mins + ":" + seconds;
        }
        else
        {
            hours = seconds / 3600;
            seconds = seconds % 3600;
            mins = seconds / 60;
            seconds = seconds % 60;

            return hours + ":" + mins + ":" + seconds;
        }

    }

    //endregion

}
