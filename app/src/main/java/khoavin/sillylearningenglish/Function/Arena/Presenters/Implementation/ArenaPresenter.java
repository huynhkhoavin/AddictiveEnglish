package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Function.Arena.Presenters.IArenaPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IArenaView;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ArenaPresenter implements IArenaPresenter {

    //Tag name
    private static final String ARENA_TAG = "ARENA_PRESENTER: ";

    //The view
    private IArenaView arenaView;

    @Inject
    IPlayerService playerService;

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
            arenaView.SetBattleChain("00000");
            arenaView.SetAvatar(currentPlayer.getAvatarUrl());
            arenaView.SetName(currentPlayer.getName());
            arenaView.SetLevel(Common.RankMedal.Sliver);
            arenaView.SetTotalBattle(currentPlayer.getTotalMatch());
            arenaView.SetWinRate(Common.GetWinRate(currentPlayer.getTotalMatch(), currentPlayer.getWinMatch()));
        }
        else
        {
            Log.e("ARENA_PRESENTER: ", "Not initialize user!");
        }
    }

}
