package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IAnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.AnswerPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IAnswerView;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/13/2017.
 */

public class AnswerActivity extends BaseDetailActivity implements IAnswerView {

    //region Const
    private final String STATE_QUESTION_WRITE = "QuestionWrite";
    private final String STATE_QUESTION_READ = "QuestionRead";
    private final String STATE_QUESTION_LISTEN = "QuestionListen";

    private final String STATE_BUTTON_A_NONE_SELECTED = "ANormal";
    private final String STATE_BUTTON_B_NONE_SELECTED = "BNormal";
    private final String STATE_BUTTON_A_TRUE_SELECTED = "ATrue";
    private final String STATE_BUTTON_A_FALSE_SELECTED = "AFalse";
    private final String STATE_BUTTON_B_TRUE_SELECTED = "BTrue";
    private final String STATE_BUTTON_B_FALSE_SELECTED = "BFalse";

    private final long VIBRATION_DURATION = 300;
    //endregion

    //region Controls
    /**
     * The total time information.
     */
    @BindView(R.id.answer_total_time)
    TextView totalTime;

    /**
     * The time progress.
     */
    @BindView(R.id.answer_time_progress)
    NumberProgressBar progressBarTime;

    /**
     * The question title.
     */
    @BindView(R.id.answer_question_title)
    TextView questionTitle;

    /**
     * The question content.
     */
    @BindView(R.id.answer_question_content)
    TextView questionContent;

    /**
     * The result B content.
     */
    @BindView(R.id.answer_result_a)
    TextView resultA;

    /**
     * The result A content.
     */
    @BindView(R.id.answer_result_b)
    TextView resultB;

    /**
     * The button listen.
     */
    @BindView(R.id.answer_button_listen)
    ImageView buttonListen;

    /**
     * The button reading
     */
    @BindView(R.id.answer_button_reading)
    ImageView buttonReading;

    /**
     * The button write.
     */
    @BindView(R.id.answer_button_write)
    ImageView buttonWrite;

    /**
     * The select answer A button.
     */
    @BindView(R.id.answer_button_selected_answer_a_normal)
    Button buttonSelectedAnswerA;

    /**
     * The select answer B button.
     */
    @BindView(R.id.answer_button_selected_answer_b_normal)
    Button buttonSelectedAnswerB;
    /**
     * The button state will be showed after user select a true or false result.
     */
    @BindView(R.id.answer_button_selected_answer_a_true_answer)
    Button buttonATrue;
    @BindView(R.id.answer_button_selected_answer_a_false_answer)
    Button buttonAFalse;

    /**
     * ...
     */
    @BindView(R.id.answer_button_selected_answer_b_false_answer)
    Button buttonBFalse;

    @BindView(R.id.answer_button_selected_answer_b_true_answer)
    Button buttonBTrue;

    @BindView(R.id.answer_block_raycast)
    ImageView blockRaycast;
    //endregion

    UIView stateQuestionItem;
    UIView stateAnswerButton;
    private IAnswerPresenter answerPresenter;

    MediaPlayer correctSoundEffect;
    MediaPlayer failsSoundEffect;
    Vibrator vibrate;

    AnswerPlayer answerPlayer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);
        setupWindowAnimations();
        setTitle(R.string.answer_title);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        correctSoundEffect = MediaPlayer.create(getApplicationContext(), R.raw.correct_sound_effect);
        failsSoundEffect = MediaPlayer.create(getApplicationContext(), R.raw.wrong_sound_effect);
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        //Initialize the answer player.
        answerPlayer = new AnswerPlayer();

        //Call register ui state method.
        RegisterUIState();

        //Call register button clicked event
        BindButtonClickedEvent();

        //Init presenter
        this.answerPresenter = new AnswerPresenter(this);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    /**
     * Register UI View state.
     */
    private void RegisterUIState() {
        stateAnswerButton = new UIView();
        stateAnswerButton.RegistryState(STATE_BUTTON_A_NONE_SELECTED, buttonSelectedAnswerA);
        stateAnswerButton.RegistryState(STATE_BUTTON_B_NONE_SELECTED, buttonSelectedAnswerB);
        stateAnswerButton.RegistryState(STATE_BUTTON_A_TRUE_SELECTED, buttonATrue);
        stateAnswerButton.RegistryState(STATE_BUTTON_B_TRUE_SELECTED, buttonBTrue);
        stateAnswerButton.RegistryState(STATE_BUTTON_A_FALSE_SELECTED, buttonAFalse);
        stateAnswerButton.RegistryState(STATE_BUTTON_B_FALSE_SELECTED, buttonBFalse);

        stateQuestionItem = new UIView();
        stateQuestionItem.RegistryState(STATE_QUESTION_LISTEN, buttonListen);
        stateQuestionItem.RegistryState(STATE_QUESTION_READ, buttonReading);
        stateQuestionItem.RegistryState(STATE_QUESTION_WRITE, buttonWrite);
    }

    /**
     * Bind button clicked event.
     */
    private void BindButtonClickedEvent() {
        this.buttonSelectedAnswerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPresenter.ChoseAnswerA();
            }
        });

        this.buttonSelectedAnswerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerPresenter.ChoseAnswerB();
            }
        });

//        this.buttonListen.setOnPositiveListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                answerPresenter.RepeatAudio();
//            }
//        });
    }

    @Override
    public void setPercentOfTimePass(float timePass) {
        progressBarTime.setProgress((int) (timePass * 100));
    }

    @Override
    public void setCurrentTime(String currentTime) {
        totalTime.setText(currentTime + " / 05:00");
    }

    @Override
    public void informAnswerResult(Common.AnswerKey answerKey, boolean result) {
        stateAnswerButton.DeactiveAllcontrol();
        switch (answerKey) {
            case A:
                if (result) {
                    stateAnswerButton.ActiveControl(STATE_BUTTON_A_TRUE_SELECTED);
                    stateAnswerButton.ActiveControl(STATE_BUTTON_B_NONE_SELECTED);
                    correctSoundEffect.start();
                } else {
                    stateAnswerButton.ActiveControl(STATE_BUTTON_A_FALSE_SELECTED);
                    stateAnswerButton.ActiveControl(STATE_BUTTON_B_NONE_SELECTED);
                    //failsSoundEffect.start();
                    VibrationEffect();
                }
                break;
            case B:
                if (result) {
                    stateAnswerButton.ActiveControl(STATE_BUTTON_A_NONE_SELECTED);
                    stateAnswerButton.ActiveControl(STATE_BUTTON_B_TRUE_SELECTED);
                    correctSoundEffect.start();
                } else {
                    stateAnswerButton.ActiveControl(STATE_BUTTON_A_NONE_SELECTED);
                    stateAnswerButton.ActiveControl(STATE_BUTTON_B_FALSE_SELECTED);
                    //failsSoundEffect.start();
                    VibrationEffect();
                }
                break;
        }
    }

    @Override
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle.setText(questionTitle);
    }

    @Override
    public void setQuestionContent(String questionContent) {
        this.questionContent.setText(questionContent);
    }

    @Override
    public void setAnswerA(String answerA) {
        this.resultA.setText(String.format(getResources().getString(R.string.question_a_title), answerA));
    }

    @Override
    public void setAnswerB(String answerB) {
        this.resultB.setText(String.format(getResources().getString(R.string.question_b_title), answerB));
    }

    @Override
    public void stopAnswerPlayer() {
        this.answerPlayer.Stop();
    }

    @Override
    public void startAnswerPlayer() {
        this.answerPlayer.Play();
    }

    @Override
    public void setMediaUrl(String mediaUrl) {
        this.answerPlayer.setMediaUrl(mediaUrl);
    }

    @Override
    public void blockRayCast(boolean block) {
        if (block)
            this.blockRaycast.setVisibility(View.VISIBLE);
        else
            this.blockRaycast.setVisibility(View.GONE);
    }


    @Override
    public void setQuestionType(Common.QuestionType questionType) {

        //Reset selected answer button state.
        this.stateAnswerButton.DeactiveAllcontrol();
        this.stateAnswerButton.ActiveControl(STATE_BUTTON_A_NONE_SELECTED);
        this.stateAnswerButton.ActiveControl(STATE_BUTTON_B_NONE_SELECTED);

        //Set question type button.
        this.stateQuestionItem.DeactiveAllcontrol();
        switch (questionType) {
            case LISTENING:
                stateQuestionItem.ActiveControl(STATE_QUESTION_LISTEN);
                break;
            case READING:
                stateQuestionItem.ActiveControl(STATE_QUESTION_READ);
                break;
            case WRITING:
                stateQuestionItem.ActiveControl(STATE_QUESTION_WRITE);
                break;
        }
    }

    /**
     * Vibration effect.
     */
    private void VibrationEffect() {
        if (vibrate != null) {
            vibrate.vibrate(VIBRATION_DURATION);
        }
    }

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
    private class AnswerPlayer {
        private MediaPlayer mediaPlayer;
        private String mediaUrl = "";
        private boolean isInitialized;

        public MediaPlayer getMediaPlayer() {
            return mediaPlayer;
        }

        public AnswerPlayer() {
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
                this.mediaUrl = url;
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