package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.Arena.CustomViews.BattleChainView;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.ArenaPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IArenaView;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/12/2017.
 */

public class ArenaActivity extends AppCompatActivity implements IArenaView{

    @BindView(R.id.win_rate_progress)
    DonutProgress donutProgress;

    @BindView(R.id.find_battle_button)
    Button findBattleButton;

    @BindView(R.id.battle_history_button)
    Button historyButton;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.coin_number)
    TextView coins;

    @BindView(R.id.total_battle)
    TextView totalBattle;

    @BindView(R.id.win_battle)
    TextView winBattle;

    @BindView(R.id.user_medal)
    ImageView rankMedal;

    @BindView(R.id.user_rank)
    TextView rankTitle;

    @BindView(R.id.battle_chain_text)
    TextView totalBattlesOnChains;

    @BindView(R.id.battle_chain_state_text)
    TextView battleChainsState;

    @BindView(R.id.user_avatar)
    ImageView userAvatar;

    private ArenaPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arena);
        setTitle(R.string.arena_title);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        ButterKnife.bind(this);

        findBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.MoveToBattlePrepare();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), BattleHistoryActivity.class);
                startActivity(intent);
            }
        });

        presenter = new ArenaPresenter(this);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }


    //region IArenaView

    @Override
    public void SetCoins(Integer coin) {
        this.coins.setText(Common.FormatCurrencyValue(coin));
    }

    @Override
    public void SetName(String name) {
        this.userName.setText(name);
    }

    @Override
    public void SetAvatar(String avatar) {
        Glide.with(this)
                .load(avatar)
                .into(userAvatar);
    }

    @Override
    public void SetTotalBattle(Integer totalBattle) {
        this.totalBattle.setText(String.valueOf(totalBattle));
    }

    @Override
    public void SetBattleChain(String battleChain, int totalBattle) {
        battleChainsState.setText(battleChain);
        totalBattlesOnChains.setText(String.format(getResources().getString(R.string.total_battles_on_chain), String.valueOf(totalBattle)));
    }

    @Override
    public void SetRankMedal(int level) {
        this.rankMedal.setImageResource(Common.getRankMedalImage(level));
    }

    @Override
    public void SetRankTitle(int level) {
        this.rankTitle.setText(Common.GetMedalTitleFromLevel(level, getBaseContext()));
    }

    @Override
    public void SetWinBattle(int winBattle) {
        this.winBattle.setText(Common.FormatBigNumber(winBattle));
    }

    @Override
    public void SetWinRateProgress(float rate) {
        this.donutProgress.setProgress(rate);
    }

    //endregion
}
