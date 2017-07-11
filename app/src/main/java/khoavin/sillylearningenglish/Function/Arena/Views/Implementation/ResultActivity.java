package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ResultPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IResultView;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.MailActivity;
import khoavin.sillylearningenglish.Function.Ranking.Views.RankingActivity;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.EnglishDictionaryController;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ResultActivity extends BaseDetailActivity implements IResultView {

    private final String STATE_BACK_TO_ARENA = "GoHome";
    private final String STATE_BACK_INBOX = "Inbox";
    private final String STATE_FIND_OTHER_BATTLE = "FindMore";
    private final String STATE_BACK_TO_RANKING = "Ranking";
    private final String STATE_BACK_TO_MAIN_HOME = "MainHome";

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

    @BindView(R.id.result_back_to_home_view)
    Button backToHomeView;

    @BindView(R.id.result_button_listen)
    ImageView buttonListen;

    @BindView(R.id.result_button_write)
    ImageView buttonWrite;

    @BindView(R.id.result_button_reading)
    ImageView buttonRead;

    @BindView(R.id.titleBar)
    LinearLayout titleBar;

    @BindView(R.id.show_dictionary)
    LinearLayout showDictionaryButton;

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
    private ResultPlayer resultPlayer;
    private EnglishDictionaryController dictionaryController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_result);
        ButterKnife.bind(this);
        setupWindowAnimations();
        setTitle(R.string.result_view_title);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        //Initialize the button state.
        buttonState = new UIView();
        buttonState.RegistryState(STATE_BACK_INBOX, backToInboxButton);
        buttonState.RegistryState(STATE_FIND_OTHER_BATTLE, findBattleButton);
        buttonState.RegistryState(STATE_BACK_TO_ARENA, goArenaButton);
        buttonState.RegistryState(STATE_BACK_TO_RANKING, backToRankingButton);
        buttonState.RegistryState(STATE_BACK_TO_MAIN_HOME, backToHomeView);

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

        if (totalAnswerTimes != 0) {
            this.SetTotalTimes(totalAnswerTimes);
        } else {
            this.SetTotalTimes(5);
        }

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        switch (battleCalledFrom) {
            case FROM_ARENA:
                //Do something to go home
                StopMediaPlayer();
                Intent goHomeIntent = new Intent(ResultActivity.this, ArenaActivity.class);
                goHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(goHomeIntent);
                break;
            case FROM_INBOX:
                StopMediaPlayer();
                Intent toMailIntent = new Intent(ResultActivity.this, MailActivity.class);
                toMailIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(toMailIntent);
                break;
            case FROM_RANKING:
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, RankingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(intent);
                break;
            case FROM_FRIEND_LIST:
                StopMediaPlayer();
                Intent goHomeView = new Intent(ResultActivity.this, HomeActivity.class);
                goHomeView.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(goHomeView);
                break;
            default:
                StopMediaPlayer();
                Intent toPrepareIntent = new Intent(ResultActivity.this, BattlePrepareActivity.class);
                toPrepareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(toPrepareIntent);
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
                transitionTo(intent);
            }
        });

        goArenaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something to go home
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, ArenaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(intent);
            }
        });

        backToInboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, MailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(intent);
            }
        });

        backToRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, RankingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(intent);
            }
        });

        backToHomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopMediaPlayer();
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                transitionTo(intent);
            }
        });

        dictionaryController = new EnglishDictionaryController(this);
        showDictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDictionaryView();
            }
        });

        presenter = new ResultPresenter(this);
    }

    //region Extra tools
    private void ShowDictionaryView() {
        dictionaryController.showDictionaryHelper();
    }
    //endregion

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
    public void SetTotalTimes(long second) {
        long sec = 0;
        long min = 0;
        min = second / 60;
        sec = second % 60;
        String secStr = String.valueOf(sec);
        String minStr = "0" + String.valueOf(min);
        if (sec < 10) secStr = "0" + secStr;
        this.totalTime.setText(minStr + ":" + secStr);
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
            case FROM_FRIEND_LIST:
                buttonState.ActiveControl(STATE_BACK_TO_MAIN_HOME);
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

    //region transition
    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }

    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        // This view will not be affected by enter transition animation
        //enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        return enterTransition;
    }
    //endregion

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
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.reset();
                }
            });
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
            } catch (Exception e) {
                Log.e("MEDIA_ERROR", e.getMessage());
            }
        }

        public void Play() {
            if (isInitialized && mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }

        public void Stop() {
            try {
                if (isInitialized && mediaPlayer != null && mediaPlayer.isPlaying()) {
                    isInitialized = false;
                    mediaPlayer.reset();
                    mediaPlayer.stop();
                }
            } catch (Exception e) {
                Log.e("MEDIA_ERROR", e.getMessage());
            }

        }
    }
}