package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/12/2017.
 */

public class BattlePrepareActivity extends AppCompatActivity implements IBattlePrepareView {

    private final String BUTTON_OTHER_ENEMY = "OtherEnemy";
    private final String BUTTON_START_BATTLE = "StartBattle";

    //region controls

    @BindView(R.id.state_start_battle_button)
    LinearLayout stateStartBattleButton;

    @BindView(R.id.state_find_battle_button)
    LinearLayout stateFindBattleButton;

    @BindView(R.id.start_battle_button)
    ImageView startBattleButton;

    @BindView(R.id.find_battle_button)
    ImageView findBattleButton;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.enemy_name)
    TextView enemyName;

    @BindView(R.id.user_win_rate)
    TextView userWinRate;

    @BindView(R.id.enemy_win_rate)
    TextView enemyWinRate;

    @BindView(R.id.user_rank_text)
    TextView userRankText;

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
    //endregion

    /**
     * The presenter.
     */
    private IBattlePreparePresenter presenter;

    /**
     * The button state.
     */
    private UIView buttonState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_prepare);
        ButterKnife.bind(this);
        setTitle(R.string.challenge);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        buttonState = new UIView();
        buttonState.RegistryState(BUTTON_OTHER_ENEMY, stateFindBattleButton);
        buttonState.RegistryState(BUTTON_START_BATTLE, stateStartBattleButton);

        //Bind button click event.
        startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.PrepareBattle();
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
        presenter = new BattlePreparePresenter(this);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

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
    public String GetBetMoney() {
        return betMoney.getText().toString();
    }

    @Override
    public String GetMessage() {
        return message.getText().toString();
    }

    @Override
    public void PreparedSuccess()
    {
        Intent it = new Intent(BattlePrepareActivity.this, AnswerActivity.class);
        startActivity(it);
        Log.e("PREPARE_ACTIVITY", "Prepare success!");
    }

    @Override
    public void PreparedFails()
    {
        Log.e("PREPARE_ACTIVITY", "Prepare fails!");
    }

    /**
     * Set button state.
     * @param calledFrom The view that called battle prepare.
     */
    @Override
    public void SetButtonState(Common.BattleCalledFrom calledFrom)
    {
        buttonState.DeactiveAllcontrol();
        switch (calledFrom)
        {
            case FROM_ARENA:
                buttonState.ActiveAllControl();
                break;
            case FROM_INBOX:
                buttonState.ActiveControl(BUTTON_START_BATTLE);
                break;
            case NOT_FOUND:
                buttonState.ActiveAllControl();
                break;
        }
    }
}
