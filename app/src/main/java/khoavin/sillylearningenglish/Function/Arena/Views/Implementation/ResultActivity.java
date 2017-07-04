package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IResultView;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.MailActivity;
import khoavin.sillylearningenglish.Function.Ranking.Views.RankingActivity;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ResultActivity extends AppCompatActivity implements IResultView {

    private final String STATE_BACK_TO_ARENA = "GoHome";
    private final String STATE_BACK_INBOX = "Inbox";
    private final String STATE_FIND_OTHER_BATTLE = "FindMore";
    private final String STATE_BACK_TO_RANKING = "Ranking";

    private final String STATE_QUESTION_READ = "ReadQuestion";
    private final String STATE_QUESTION_LISTEN = "ListenQuestion";
    private final String STATE_QUESTION_WRITE = "WriteQuestion";

    @BindView(R.id.result_question_title)
    TextView questionTitle;

    @BindView(R.id.result_question_content)
    TextView questionContent;

    @BindView(R.id.result_answer_a)
    TextView answerA;

    @BindView(R.id.result_answer_b)
    TextView answerB;

    @BindView(R.id.result_total_time)
    TextView totalTime;

    @BindView(R.id.result_back_to_prepare)
    Button findBattleButton;

    @BindView(R.id.result_back_to_arena)
    Button goArenaButton;

    @BindView(R.id.result_back_to_inbox)
    Button backToInboxButton;

    @BindView(R.id.result_back_to_ranking)
    Button backToRankingButton;

    @BindView(R.id.result_button_listen)
    ImageView buttonListen;

    @BindView(R.id.result_button_write)
    ImageView buttonWrite;

    @BindView(R.id.result_button_reading)
    ImageView buttonRead;

    Button[] answerButtons;

    /**
     * The button state manager.
     */
    UIView buttonState;
    UIView questionState;
    Common.BattleCalledFrom battleCalledFrom = Common.BattleCalledFrom.NOT_FOUND;

    private int defaultColor;
    private int trueAnswerColor;


    //The presenter
    ResultPresenter presenter;

    private boolean[] answerState;

    /**
     * The result player.
     */
    private ResultPlayer resultPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_result);
        ButterKnife.bind(this);
        setTitle(R.string.result_view_title);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        //Initialize the button state.
        buttonState = new UIView();
        buttonState.RegistryState(STATE_BACK_INBOX, backToInboxButton);
        buttonState.RegistryState(STATE_FIND_OTHER_BATTLE, findBattleButton);
        buttonState.RegistryState(STATE_BACK_TO_ARENA, goArenaButton);
        buttonState.RegistryState(STATE_BACK_TO_RANKING, backToRankingButton);

        //Initialize the question type state.
        questionState = new UIView();
        questionState.RegistryState(STATE_QUESTION_LISTEN, buttonListen);
        questionState.RegistryState(STATE_QUESTION_READ, buttonRead);
        questionState.RegistryState(STATE_QUESTION_WRITE, buttonWrite);

        //Initialize the result player.
        this.resultPlayer = new ResultPlayer();

        Initialize();

        int totalAnswerTimes = 0;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                totalAnswerTimes = 0;
            } else {
                totalAnswerTimes = extras.getInt("TOTAL_ANSWER_TIMES");
            }
        } else {
            totalAnswerTimes = 0;
        }

        if(totalAnswerTimes != 0)
        {
            this.SetTotalTimes(totalAnswerTimes * 1000);
        }
        else
        {
            this.SetTotalTimes(5 * 1000);
        }
    }

    @Override
    public void onBackPressed() {
        switch (battleCalledFrom) {
            case FROM_ARENA:
                //Do something to go home
                Intent goHomeIntent = new Intent(ResultActivity.this, ArenaActivity.class);
                goHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goHomeIntent);
                break;
            case FROM_INBOX:
                Intent toMailIntent = new Intent(ResultActivity.this, MailActivity.class);
                toMailIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(toMailIntent);
                break;
            case FROM_RANKING:
                Intent intent = new Intent(ResultActivity.this, RankingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                Intent toPrepareIntent = new Intent(ResultActivity.this, BattlePrepareActivity.class);
                toPrepareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(toPrepareIntent);
        }
    }

    private void Initialize() {
        this.trueAnswerColor = getResources().getColor(R.color.Green);
        this.defaultColor = getResources().getColor(R.color.BlackText);

        answerButtons = new Button[5];
        this.answerButtons[0] = (Button) findViewById(R.id.result_answer_1);
        this.answerButtons[1] = (Button) findViewById(R.id.result_answer_2);
        this.answerButtons[2] = (Button) findViewById(R.id.result_answer_3);
        this.answerButtons[3] = (Button) findViewById(R.id.result_answer_4);
        this.answerButtons[4] = (Button) findViewById(R.id.result_answer_5);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++) {
                    if (v == answerButtons[i]) {
                        presenter.ShowQuestionWithIndex(i);
                        break;
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
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, BattlePrepareActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        goArenaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something to go home
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, ArenaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        backToInboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, MailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        backToRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, RankingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        presenter = new ResultPresenter(this);
    }

    // region Implementation
    @Override
    public void HeightLineTrueAnswer(Common.AnswerKey answerKey) {
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
    public void setQuestionType(Common.QuestionType questionType) {
        switch (questionType) {
            case LISTENING:
                questionState.ControlState(STATE_QUESTION_LISTEN);
                break;
            case READING:
                questionState.ControlState(STATE_QUESTION_READ);
                break;
            case WRITING:
                questionState.ControlState(STATE_QUESTION_WRITE);
                break;
        }
    }

    @Override
    public void SetAnswerA(String answerA) {
        this.answerA.setText(String.format(getResources().getString(R.string.question_a_title), answerA));
    }

    @Override
    public void SetAnswerB(String answerB) {
        this.answerB.setText(String.format(getResources().getString(R.string.question_b_title), answerB));
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
        this.answerState = answerState;
        if (answerState.length != 5) return;
        for (int i = 0; i < answerState.length; i++) {
            if (answerState[i]) {
                answerButtons[i].setBackground(getResources().getDrawable(R.drawable.result_true_answer));
            } else {
                answerButtons[i].setBackground(getResources().getDrawable(R.drawable.result_wrong_answer));
            }
        }
    }

    @Override
    public void SetTotalTimes(long millisecond) {
        this.totalTime.setText(Common.GetSillyDateFormat().MillisecondToString(millisecond));
    }

    @Override
    public void SetButtonState(Common.BattleCalledFrom calledFrom) {
        battleCalledFrom = calledFrom;
        buttonState.DeactiveAllcontrol();
        switch (calledFrom) {
            case FROM_ARENA:
                buttonState.ActiveControl(STATE_BACK_TO_ARENA);
                buttonState.ActiveControl(STATE_FIND_OTHER_BATTLE);
                break;
            case FROM_INBOX:
                buttonState.ActiveControl(STATE_BACK_INBOX);
                break;
            case FROM_RANKING:
                buttonState.ActiveControl(STATE_BACK_TO_RANKING);
                break;
            default:
                buttonState.ActiveControl(STATE_FIND_OTHER_BATTLE);
                break;
        }
    }

    /**
     * ...
     *
     * @param index
     */
    @Override
    public void HighlighSelectedAnswer(int index) {
        if (answerState == null || answerState.length != 5) return;
        for (int i = 0; i < answerState.length; i++) {
            if (i == index) {
                if (answerState[i])
                    answerButtons[i].setBackground(getResources().getDrawable(R.drawable.result_true_answer_preview));
                else
                    answerButtons[i].setBackground(getResources().getDrawable(R.drawable.result_wrong_answer_preview));
            } else if (answerState[i]) {
                answerButtons[i].setBackground(getResources().getDrawable(R.drawable.result_true_answer));
            } else {
                answerButtons[i].setBackground(getResources().getDrawable(R.drawable.result_wrong_answer));
            }
        }
    }

    @Override
    public void SetMediaUrl(String mediaUrl) {
        this.resultPlayer.setMediaUrl(mediaUrl);
        this.resultPlayer.Play();
    }

    @Override
    public void StopMedia() {
        this.resultPlayer.Stop();
    }

    private void StopMediaPlayer() {
        if (this.resultPlayer != null)
            resultPlayer.Stop();
    }

    // endregion

    /**
     * The answer player.
     */
    private class ResultPlayer {
        private MediaPlayer mediaPlayer;
        //private String mediaUrl = "";
        private boolean isInitialized;

        public MediaPlayer getMediaPlayer() {
            return mediaPlayer;
        }

        public ResultPlayer() {
            mediaPlayer = new MediaPlayer();
        }

        public void setMediaUrl(String url) {
            //Stop current player.
            if (isInitialized)
                Stop();

            //Reset initialize state.
            isInitialized = false;
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                isInitialized = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void Play() {
            if (isInitialized && mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }

        public void Stop() {
            if (isInitialized && mediaPlayer != null && mediaPlayer.isPlaying()) {
                isInitialized = false;
                mediaPlayer.reset();
                mediaPlayer.stop();
//                mediaPlayer.release();
            }
        }
    }
}