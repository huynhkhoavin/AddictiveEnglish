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
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ArenaPresenter {

    /**
     * The arena view.
     */
    private IArenaView arenaView;
    private AppCompatActivity GetView(){return (AppCompatActivity)arenaView;}

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

    public ArenaPresenter(final IArenaView arenaView)
    {
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
    private void RefreshUserInformation()
    {
        User currentPlayer = playerService.GetCurrentUser();
        if(currentPlayer != null)
        {
            arenaView.SetCoins(currentPlayer.getCoin());
            arenaView.SetBattleChain("01123");
            arenaView.SetAvatar(currentPlayer.getAvatarUrl());
            arenaView.SetName(currentPlayer.getName());
            arenaView.SetRankTitle(currentPlayer.getLevel());
            arenaView.SetRankMedal(currentPlayer.getLevel());
            arenaView.SetWinBattle(currentPlayer.getWinMatch());
            arenaView.SetTotalBattle(currentPlayer.getTotalMatch());
            arenaView.SetWinRateProgress(Common.GetWinRateAsFloat(currentPlayer.getTotalMatch(), currentPlayer.getWinMatch()));
        }
        else
        {
            Log.e("ARENA_PRESENTER: ", "Not initialize user!");
        }
    }

    /**
     * Move to battle prepare.
     */
    public void MoveToBattlePrepare()
    {
        arenaService.SetBattleCalledFrom(Common.BattleCalledFrom.FROM_ARENA);
        Intent it = new Intent(GetView(), BattlePrepareActivity.class);
        GetView().startActivity(it);
    }

}
