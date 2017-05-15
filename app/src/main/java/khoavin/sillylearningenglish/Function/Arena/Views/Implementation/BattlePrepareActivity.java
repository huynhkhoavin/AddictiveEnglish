package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import khoavin.sillylearningenglish.Function.Arena.Presenters.IBattlePreparePresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.BattlePreparePresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IBattlePrepareView;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/12/2017.
 */

public class BattlePrepareActivity extends AppCompatActivity implements IBattlePrepareView {

    /// <sumary>
    /// All BattlePrepareActivity View component
    /// </sumary>
    ImageView startBattleButton;
    ImageView findBattleButton;
    TextView userName;
    TextView enemyName;
    TextView userWinRate;
    TextView enemyWinRate;
    TextView userRankText;
    TextView enemyRankText;
    EditText betMoney;
    EditText message;
    ImageView userAvatar;
    ImageView enemyAvatar;


    /// <sumary>
    /// The battle prepare presenter
    /// </sumary>
    private IBattlePreparePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_prepare);
        setTitle(R.string.challenge);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        //Init all controls
        InitAllControls();
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    private void InitAllControls()
    {
        //Install action button click event
        startBattleButton = (ImageView)findViewById(R.id.start_battle_button);
        startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.PrepareBattle();
                Log.d("PREPARE_ACTIVITY: ", "Do something to start battle!");
            }
        });

        findBattleButton = (ImageView)findViewById(R.id.find_battle_button);
        findBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PREPARE_ACTIVITY: ", "Do something to find other enemy!");
                presenter.FindOtherEnemy();
            }
        });

        //Install textview, image view, edittext,...
        userName = (TextView)findViewById(R.id.user_name);
        enemyName= (TextView)findViewById(R.id.enemy_name);
        userWinRate = (TextView)findViewById(R.id.user_win_rate);
        enemyWinRate = (TextView)findViewById(R.id.enemy_win_rate);
        userRankText = (TextView)findViewById(R.id.user_rank_text);
        enemyRankText = (TextView)findViewById(R.id.enemy_rank_text);
        betMoney = (EditText) findViewById(R.id.bet_money);
        message = (EditText) findViewById(R.id.message);
        userAvatar = (ImageView) findViewById(R.id.user_avatar);
        enemyAvatar = (ImageView) findViewById(R.id.enemy_avatar);

        //Initialize view via presenter
        presenter = new BattlePreparePresenter(this);
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
}
