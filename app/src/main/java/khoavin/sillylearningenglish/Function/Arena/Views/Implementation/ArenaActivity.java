package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import khoavin.sillylearningenglish.Function.Arena.CustomViews.BattleChainView;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IArenaPresenter;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ArenaPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IArenaView;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/12/2017.
 */

public class ArenaActivity extends AppCompatActivity implements IArenaView{

    private ImageView findBattleButton;
    private TextView userName;
    private TextView coins;
    private TextView totalBattle;
    private TextView winRate;
    private ImageView medal;
    private TextView rank;
    private BattleChainView battleChains;
    private ImageView userAvatar;

    private IArenaPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle(R.string.arena_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        LoadAllControls();

        presenter = new ArenaPresenter(this);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    private void LoadAllControls()
    {
        findBattleButton = (ImageView) findViewById(R.id.find_battle_button);
        findBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ArenaActivity.this, BattlePrepareActivity.class);
                startActivity(it);
            }
        });

        userName = (TextView)findViewById(R.id.user_name);
        coins  = (TextView)findViewById(R.id.coin_number);
        totalBattle = (TextView)findViewById(R.id.total_battle);
        winRate = (TextView)findViewById(R.id.win_rate);
        medal = (ImageView) findViewById(R.id.user_medal);
        rank = (TextView)findViewById(R.id.user_rank);
        battleChains = (BattleChainView) findViewById(R.id.battle_chain);
        userAvatar = (ImageView)findViewById(R.id.user_avatar);
    }


    //region IArenaView

    @Override
    public void SetCoins(Integer coin) {
        this.coins.setText(String.valueOf(coin));
    }

    @Override
    public void SetName(String name) {
        this.userName.setText(name);
    }

    @Override
    public void SetAvatar(Uri avatar) {
        Glide.with(this)
                .load(avatar)
                .into(userAvatar);
    }

    @Override
    public void SetTotalBattle(Integer totalBattle) {
        this.totalBattle.setText(String.valueOf(totalBattle));
    }

    @Override
    public void SetWinRate(String winRate) {
        this.winRate.setText(winRate);
    }

    @Override
    public void SetLevel(Common.RankMedal rankMedal) {
        this.rank.setText(rankMedal + " [BẠC A]");
    }

    @Override
    public void SetBattleChain(String battleChain) {
        this.battleChains.SetBattleState(battleChain);
    }

    //endregion
}
