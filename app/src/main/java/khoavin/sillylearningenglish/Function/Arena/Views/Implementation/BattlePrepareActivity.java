package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import khoavin.sillylearningenglish.Function.Arena.Views.IBattlePrepareView;
import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 2/12/2017.
 */

public class BattlePrepareActivity extends AppCompatActivity implements IBattlePrepareView {

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
        startBattleButton = (ImageView)findViewById(R.id.start_battle_button);
        startBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(BattlePrepareActivity.this, AnswerActivity.class);
                startActivity(it);
            }
        });

        findBattleButton = (ImageView)findViewById(R.id.find_battle_button);
        findBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        userName = (TextView)findViewById(R.id.user_name);
        enemyName= (TextView)findViewById(R.id.enemy_name);
        userWinRate = (TextView)findViewById(R.id.user_win_rate);
        enemyWinRate = (TextView)findViewById(R.id.enemy_win_rate);
        userRankText = (TextView)findViewById(R.id.user_rank_text);
        enemyRankText = (TextView)findViewById(R.id.enemy_rank_text);
        betMoney = (EditText) findViewById(R.id.bet_money);
        message = (EditText) findViewById(R.id.message);
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
    public void SetUserAvatar(String userAvater) {
        //Load avatar from url
    }

    @Override
    public void SetEnemyAvatar(String enemyAvatar) {
        //Load avatar from url
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
}
