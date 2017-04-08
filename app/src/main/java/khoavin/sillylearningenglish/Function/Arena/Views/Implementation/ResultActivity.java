package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import khoavin.sillylearningenglish.Function.Arena.Presenters.IResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.AnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IResultView;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ResultActivity extends AppCompatActivity implements IResultView {

    //All controls
    TextView questionTitle;
    TextView questionContent;
    TextView answerA;
    TextView answerB;
    ImageView hearImage;
    TextView totalTime;

    //All buttons
    ImageView findBattleButton;
    ImageView goHomeButton;
    ImageView[] answerButtons;

    //All resource id
    private int defaultColor;
    private int trueAnswerColor;
    Drawable trueButtonId;
    Drawable falseButtonId;

    //The presenter
    IResultPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_result);
        setTitle(R.string.result_view_title);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        Initialize();
    }

    private void Initialize() {
        //Get drawable and color
        this.trueAnswerColor = getResources().getColor(R.color.Green);
        this.defaultColor = getResources().getColor(R.color.BlackText);
        this.trueButtonId = getResources().getDrawable(R.drawable.true_icon);
        this.falseButtonId = getResources().getDrawable(R.drawable.false_icon);

        //Get controls
        answerButtons = new ImageView[5];
        this.questionTitle = (TextView) findViewById(R.id.question_title);
        this.questionContent = (TextView) findViewById(R.id.question);
        this.answerA = (TextView) findViewById(R.id.answer_a);
        this.answerB = (TextView) findViewById(R.id.answer_b);
        this.hearImage = (ImageView) findViewById(R.id.icon_playing);
        this.totalTime = (TextView) findViewById(R.id.total_time);

        this.answerButtons[0] = (ImageView) findViewById(R.id.your_answer_1);
        this.answerButtons[1] = (ImageView) findViewById(R.id.your_answer_2);
        this.answerButtons[2] = (ImageView) findViewById(R.id.your_answer_3);
        this.answerButtons[3] = (ImageView) findViewById(R.id.your_answer_4);
        this.answerButtons[4] = (ImageView) findViewById(R.id.your_answer_5);
        this.findBattleButton = (ImageView) findViewById(R.id.find_battle_button);
        this.goHomeButton = (ImageView) findViewById(R.id.go_home_button);

        presenter = new ResultPresenter(this);
        //Initialize click
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++) {
                    if (v == answerButtons[i]) {
                        presenter.ShowQuestionWithIndex(i);
                    }
                }

            }
        };

        for (int i = 0; i < 5; i++) {
            this.answerButtons[i].setOnClickListener(onClickListener);
        }

        findBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Find other battle
            }
        });

        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something to go home
            }
        });
    }


    // region Implementation
    @Override
    public void HighlineTrueAnswer(Common.AnswerKey answerKey) {
        switch (answerKey) {
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

    @Override
    public void HideOrShowHearIcon(boolean show) {
        if (show)
            this.hearImage.setVisibility(View.VISIBLE);
        else
            this.hearImage.setVisibility(View.GONE);
    }

    @Override
    public void SetAnswerA(String answerA) {
        this.answerA.setText(answerA);
    }

    @Override
    public void SetAnswerB(String answerB) {
        this.answerB.setText(answerB);
    }

    @Override
    public void SetQuestionContent(String questionContent) {
        this.questionContent.setText(questionContent);
    }

    @Override
    public void SetQuestionTitle(String questionTitle) {
        this.questionTitle.setText(questionTitle);
    }

    @Override
    public void SetAnswerArrayButton(boolean[] answerState) {
        if (answerState.length != 5) return;
        for (int i = 0; i < answerState.length; i++) {
            if (answerState[i]) {
                answerButtons[i].setBackground(trueButtonId);
            } else {
                answerButtons[i].setBackground(falseButtonId);
            }
        }
    }

    @Override
    public void SetTotalTimes(long milisecond) {
        this.totalTime.setText(Common.MillisecondToString(milisecond));
    }

    // endregion
}