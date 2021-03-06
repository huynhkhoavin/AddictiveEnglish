package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IArenaPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IArenaView;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.ArenaActivity;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.BattlePrepareActivity;
import khoavin.sillylearningenglish.NetworkService.Implementation.PlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ChainInfo;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ArenaPresenter {

    /**
     * The arena view.
     */
    private IArenaView arenaView;

    private BaseDetailActivity GetView() {
        return (BaseDetailActivity) arenaView;
    }

    /**
     * The player service.
     */
    @Inject
    IPlayerService playerService;

    /**
     * The arena service.
     */
    @Inject
    IArenaService arenaService;

    @Inject
    IVolleyService volleyService;

    public ArenaPresenter(final IArenaView arenaView) {
        this.arenaView = arenaView;

        //inject arena service
        ((SillyApp) ((AppCompatActivity) arenaView).getApplication())
                .getDependencyComponent()
                .inject(this);

        RefreshUserInformation();

    }

    /**
     * Refresh user information on arena view
     */
    private void RefreshUserInformation() {
        User currentPlayer = playerService.GetCurrentUser();
        if (currentPlayer != null) {
            arenaView.SetCoins(currentPlayer.getCoin());
            arenaView.SetAvatar(currentPlayer.getAvatarUrl());
            arenaView.SetName(currentPlayer.getName());
            arenaView.SetRankTitle(currentPlayer.getLevel());
            arenaView.SetRankMedal(currentPlayer.getLevel());
            arenaView.SetWinBattle(currentPlayer.getWinMatch());
            arenaView.SetTotalBattle(currentPlayer.getTotalMatch());
            arenaView.SetWinRateProgress(Common.GetWinRateAsFloat(currentPlayer.getTotalMatch(), currentPlayer.getWinMatch()));
            GetBattleChains();
        } else {
            Log.e("ARENA_PRESENTER: ", "Not initialize user!");
        }
    }

    /**
     * Move to battle prepare.
     */
    public void MoveToBattlePrepare() {
        arenaService.SetBattleCalledFrom(Common.BattleCalledFrom.FROM_ARENA);
        Intent it = new Intent(GetView(), BattlePrepareActivity.class);
        GetView().transitionTo(it);
    }

    private void GetBattleChains() {
        arenaService.GetBattleChains(playerService.GetCurrentUser().getUserId(), GetView(), volleyService, new IVolleyResponse<ChainInfo[]>() {
            @Override
            public void onSuccess(ChainInfo[] infos) {
                int currentBattleOnChain = playerService.GetCurrentUser().getCurrentBattle();
                int totalBattle = 1;
                switch (Common.GetMedalFromLevel(playerService.GetCurrentUser().getLevel())) {
                    case Bronze:
                        totalBattle = 1;
                        break;
                    case Gold:
                        totalBattle = 5;
                        break;
                    case Sliver:
                        totalBattle = 3;
                        break;
                }
                if (infos == null || infos.length == 0) {
                    arenaView.SetBattleChain(FormatBattleChains(0, 0), totalBattle);
                } else {
                    int vicB = 0;
                    int falB = 0;
                    for (int i = 0; i < currentBattleOnChain && i < totalBattle && i < infos.length; i++) {
                        if (infos[i].getVictoryId().equals("1")) {
                            vicB++;
                        } else if (infos[i].getVictoryId().equals("-1")) {
                            falB++;
                        }
                    }

                    arenaView.SetBattleChain(FormatBattleChains(vicB, falB), totalBattle);
                }
            }

            @Override
            public void onError(ErrorCode errorCode) {

            }
        });
    }

    private String FormatBattleChains(int winBattle, int lostBattle) {
        return String.format(GetView().getResources().getString(R.string.win_lost_battle_chain), String.valueOf(winBattle), String.valueOf(lostBattle));
    }


}
