package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IBattlePreparePresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.BattlePreparePresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IBattlePrepareView;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.AppParam;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/12/2017.
 */

public class BattlePrepareActivity extends BaseDetailActivity implements IBattlePrepareView {

    private final String BUTTON_OTHER_ENEMY = "OtherEnemy";
    private final String BUTTON_START_BATTLE = "StartBattle";
    private final String BUTTON_CANCEL_BATTLE = "CancelBattle";
    private final String BUTTON_ACEPT_BATTLE = "AcceptBattle";

    private final int MAX_BET = 10000;

    //region controls

    @BindView(R.id.start_battle_button)
    Button startBattleButton;

    @BindView(R.id.find_battle_button)
    Button findBattleButton;

    @BindView(R.id.cancel_battle_button)
    Button cancelBattleButton;

    @BindView(R.id.accept_battle_button)
    Button accceptBattleButton;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.enemy_name)
    TextView enemyName;

    @BindView(R.id.user_win_rate)
    TextView userWinRate;

    @BindView(R.id.enemy_win_rate)
    TextView enemyWinRate;

    @BindView((R.id.user_rank))
    ImageView userRankMedal;

    @BindView(R.id.user_rank_text)
    TextView userRankText;

    @BindView(R.id.enemy_rank)
    ImageView enemyRankMedal;

    @BindView(R.id.enemy_rank_text)
    TextView enemyRankText;

    @BindView(R.id.bet_money)
    EditText betMoney;

    @BindView(R.id.message)
    EditText message;

    @BindView(R.id.user_avatar)
    ImageView userAvatar;

    @BindView(R.id.enemy_avatar)
    ImageView enemyAvatar;

    @BindView(R.id.titleBar)
    LinearLayout titleBar;
    //endregion

    /**
     * The presenter.
     */
    private IBattlePreparePresenter presenter;

    /**
     * The button state.
     */
    private UIView buttonState;
    private int minBetValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_prepare);
        ButterKnife.bind(this);
        setupWindowAnimations();
        setTitle(R.string.challenge);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        buttonState = new UIView();
        buttonState.RegistryState(BUTTON_OTHER_ENEMY, findBattleButton);
        buttonState.RegistryState(BUTTON_START_BATTLE, startBattleButton);
        buttonState.RegistryState(BUTTON_CANCEL_BATTLE, cancelBattleButton);
        buttonState.RegistryState(BUTTON_ACEPT_BATTLE, accceptBattleButton);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Bind button click event.
        startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.CreateBattle();
                Log.d("PREPARE_ACTIVITY: ", "Do something to start battle!");
            }
        });

        findBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PREPARE_ACTIVITY: ", "Do something to find other enemy!");
                presenter.FindOtherEnemy();
            }
        });

        cancelBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PREPARE_ACTIVITY: ", "Use just cancel battle.");
                presenter.CancelBattle();
            }
        });

        accceptBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.AcceptBattle();
                Log.d("PREPARE_ACTIVITY: ", "Use just accept battle.");
            }
        });

        //Check value when end edit.
        betMoney.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Check the bet value.
                    CheckBetValue();
                    return true;
                }
                return false;
            }
        });

        //Check value when edit text lost focus
        betMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    CheckBetValue();
                }
            }
        });

        int battleIdentifier;
        long battleBetValue;
        int battleMailIndentifier;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                battleIdentifier = -1;
                battleBetValue = -1;
                battleMailIndentifier = -1;
            } else {
                battleIdentifier = extras.getInt("BATTLE_IDENTIFIER");
                battleBetValue = extras.getLong("BATTLE_BET_VALUE");
                battleMailIndentifier = extras.getInt("BATTLE_MAIL_IDENTIFIER");
            }
        } else {
            battleIdentifier = -1;
            battleBetValue = -1;
            battleMailIndentifier = -1;
        }

        presenter = new BattlePreparePresenter(this, battleIdentifier, battleBetValue, battleMailIndentifier);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
//    }

    @Override
    public void SetUserName(String userName) {
        this.userName.setText(userName);
    }

    @Override
    public void SetEnemyName(String enemyName) {
        this.enemyName.setText(enemyName);
    }

    @Override
    public void SetUserAvatar(String uAvatar) {
        //Load avatar from url
        Glide.with(this)
                .load(uAvatar)
                .into(userAvatar);
    }

    @Override
    public void SetEnemyAvatar(String eAvatar) {
        //Load avatar from url
        Glide.with(this)
                .load(eAvatar)
                .into(enemyAvatar);
    }

    @Override
    public void SetUserWinRate(String userWinRate) {
        this.userWinRate.setText(userWinRate);
    }

    @Override
    public void SetEnemyWinRate(String enemyWinRate) {
        this.enemyWinRate.setText(enemyWinRate);
    }

    @Override
    public void SetUserRankText(String userRankText) {
        this.userRankText.setText(userRankText);
    }

    @Override
    public void SetEnemyRankText(String enemyRankText) {
        this.enemyRankText.setText(enemyRankText);
    }

    @Override
    public void SetUserRankMedal(int medalId) {
        this.userRankMedal.setImageResource(medalId);
    }

    @Override
    public void SetEnemyRankMedal(int medalId) {
        this.enemyRankMedal.setImageResource(medalId);
    }

    @Override
    public String GetBetMoney() {
        return betMoney.getText().toString();
    }

    @Override
    public String GetMessage() {
        return message.getText().toString();
    }

    @Override
    public void PreparedSuccess() {
        Intent it = new Intent(BattlePrepareActivity.this, AnswerActivity.class);
        transitionTo(it);
        Log.e("PREPARE_ACTIVITY", "Prepare success!");
    }

    @Override
    public void PreparedFails() {
        Log.e("PREPARE_ACTIVITY", "Prepare fails!");
    }

    /**
     * Set button state.
     *
     * @param calledFrom The view that called battle prepare.
     */
    @Override
    public void SetButtonState(Common.BattleCalledFrom calledFrom) {
        buttonState.DeactiveAllcontrol();
        switch (calledFrom) {
            case FROM_ARENA:
                this.betMoney.setFocusableInTouchMode(true);
                this.message.setFocusableInTouchMode(true);
                buttonState.ActiveControl(BUTTON_START_BATTLE);
                buttonState.ActiveControl(BUTTON_OTHER_ENEMY);
                break;
            case FROM_INBOX:
                this.betMoney.setFocusableInTouchMode(false);
                this.message.setFocusableInTouchMode(false);
                buttonState.ActiveControl(BUTTON_ACEPT_BATTLE);
                buttonState.ActiveControl(BUTTON_CANCEL_BATTLE);
                break;
            case FROM_RANKING:
                betMoney.setFocusableInTouchMode(true);
                message.setFocusableInTouchMode(true);
                buttonState.ActiveControl(BUTTON_START_BATTLE);
                buttonState.ActiveControl(BUTTON_OTHER_ENEMY);
            case  FROM_FRIEND_LIST:
                this.betMoney.setFocusableInTouchMode(true);
                this.message.setFocusableInTouchMode(true);
                buttonState.ActiveControl(BUTTON_START_BATTLE);
                buttonState.ActiveControl(BUTTON_OTHER_ENEMY);
                break;
            case NOT_FOUND:
                this.betMoney.setFocusableInTouchMode(true);
                this.message.setFocusableInTouchMode(true);
                buttonState.ActiveControl(BUTTON_START_BATTLE);
                buttonState.ActiveControl(BUTTON_OTHER_ENEMY);
                break;
        }
    }

    @Override
    public void SetBetValue(long betValue) {
        this.betMoney.setText(String.valueOf(betValue));
    }

    @Override
    public void SetMinBetValue(int minBetValue) {
        this.minBetValue = minBetValue;
        betMoney.setText(String.valueOf(minBetValue));
    }

    /**
     * Check the bet value.
     */
    private void CheckBetValue() {
        //Accepted bet value between 2000 and 10000.
        try {
            String stringValue = betMoney.getText().toString();
            if (!stringValue.equals("")) {
                int betVal = Integer.valueOf(betMoney.getText().toString());
                if (betVal < minBetValue)
                    betMoney.setText(String.valueOf(minBetValue));
                else if (betVal > MAX_BET)
                    betMoney.setText(String.valueOf(MAX_BET));
                else
                    betMoney.setText(String.valueOf(minBetValue));
            } else {
                betMoney.setText(String.valueOf(minBetValue));
            }
        } catch (ParseException ex) {
            Log.e("PREPARE_ACTIVITY", "Fails to parse bet value");
            betMoney.setText(String.valueOf(minBetValue));
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
}
