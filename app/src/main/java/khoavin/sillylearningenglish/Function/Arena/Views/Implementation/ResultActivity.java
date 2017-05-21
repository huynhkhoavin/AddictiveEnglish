package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IResultView;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.MailActivity;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ResultActivity extends AppCompatActivity implements IResultView {

    private final String STATE_GO_HOME = "GoHome";
    private final String STATE_BACK_INBOX = "Inbox";
    private final String STATE_FIND_OTHER_BATTLE = "FindMore";


    /*
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
     */
    @BindView(R.id.question_title)
    TextView questionTitle;

    @BindView(R.id.question)
    TextView questionContent;

    @BindView(R.id.answer_a)
    TextView answerA;

    @BindView(R.id.answer_b)
    TextView answerB;

    @BindView(R.id.icon_playing)
    ImageView hearImage;

    @BindView(R.id.total_time)
    TextView totalTime;

    @BindView(R.id.find_battle_button)
    ImageView findBattleButton;

    @BindView(R.id.go_home_button)
    ImageView goHomeButton;

    @BindView(R.id.back_to_inbox)
    ImageView backToInboxButton;

    ImageView[] answerButtons;

    @BindView(R.id.state_back_to_inbox)
    LinearLayout backToInBoxButtonState;

    @BindView(R.id.state_back_to_home)
    LinearLayout backToHomeButtonState;

    @BindView(R.id.back_to_prepare)
    LinearLayout backToPrepareButtonState;

    /**
     * The button state manager.
     */
    UIView buttonState;


    private int defaultColor;
    private int trueAnswerColor;
    Drawable trueButtonId;
    Drawable falseButtonId;

    //The presenter
    ResultPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_result);
        ButterKnife.bind(this);
        setTitle(R.string.result_view_title);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        buttonState = new UIView();
        buttonState.RegistryState(STATE_BACK_INBOX, backToInBoxButtonState);
        buttonState.RegistryState(STATE_FIND_OTHER_BATTLE, backToPrepareButtonState);
        buttonState.RegistryState(STATE_GO_HOME, backToHomeButtonState);

        Initialize();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, ArenaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void Initialize() {
        //Get drawable and color
        this.trueAnswerColor = getResources().getColor(R.color.Green);
        this.defaultColor = getResources().getColor(R.color.BlackText);
        this.trueButtonId = getResources().getDrawable(R.drawable.true_icon);
        this.falseButtonId = getResources().getDrawable(R.drawable.false_icon);

        answerButtons = new ImageView[5];
        this.answerButtons[0] = (ImageView) findViewById(R.id.your_answer_1);
        this.answerButtons[1] = (ImageView) findViewById(R.id.your_answer_2);
        this.answerButtons[2] = (ImageView) findViewById(R.id.your_answer_3);
        this.answerButtons[3] = (ImageView) findViewById(R.id.your_answer_4);
        this.answerButtons[4] = (ImageView) findViewById(R.id.your_answer_5);
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
                Intent intent = new Intent(ResultActivity.this, BattlePrepareActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something to go home
                Intent intent = new Intent(ResultActivity.this, ArenaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        backToInboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        presenter = new ResultPresenter(this);
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

    @Override
    public void SetButtonState(Common.BattleCalledFrom calledFrom) {
        buttonState.DeactiveAllcontrol();
        switch (calledFrom) {
            case FROM_ARENA:
                buttonState.ActiveControl(STATE_GO_HOME);
                buttonState.ActiveControl(STATE_FIND_OTHER_BATTLE);
                break;
            case FROM_INBOX:
                buttonState.ActiveControl(STATE_BACK_INBOX);
                break;
        }
    }

    // endregion
}