package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Function.Arena.Presenters.IBattlePreparePresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IBattlePrepareView;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Implementation.ArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.SingleViewObject.Common;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by OatOal on 2/18/2017.
 */

public class BattlePreparePresenter implements IBattlePreparePresenter {

    /// <sumary>
    /// The log tag
    /// </sumary>
    private static final String BATTLE_PREPARE_TAG = "PREPARE_PRESENTER: ";

    /// <Sumary>
    /// The battle prepare view
    /// </Sumary>
    private IBattlePrepareView prepareView;

    ///The finding and stating flag
    private boolean finding, stating;

    /// <Sumary>
    /// The arena service
    /// </Sumary>
    @Inject
    IArenaService arenaService;

    @Inject
    IPlayerService playerService;

    public BattlePreparePresenter(final IBattlePrepareView prepareView)
    {
        this.prepareView = prepareView;
        ((SillyApp)((AppCompatActivity) this.prepareView).getApplication())
                .getDependencyComponent()
                .inject(this);

        InitView();
    }

    @Override
    public void FindOtherEnemy() {
        FindBattle();
    }

    @Override
    public void PrepareBattle() {
        if(!CheckCondition()
            || stating
            || arenaService.GetCurrentEnemy() == null)
        {
            return;
        }

        stating = true;
        String enemyId = arenaService.GetCurrentEnemy().getUserId();
        String userId = playerService.GetCurrentUser().getUserId();
        arenaService.CreateBattle(userId, enemyId, Integer.valueOf(prepareView.GetBetMoney()), prepareView.GetMessage(), new IServerResponse<Questions>() {
            @Override
            public void onSuccess(Questions responseObj) {
                prepareView.PreparedSuccess();
                stating = false;
            }

            @Override
            public void onError(SillyError sillyError) {
                prepareView.PreparedFails();
                stating = false;
            }
        });
    }

    /// <Sumary>
    /// Initialize the view
    /// </Sumary>
    private void InitView()
    {
        if(arenaService == null)
        {
            Log.e(BATTLE_PREPARE_TAG, "The arena service not initialized yet!");
            return;
        }

        if(playerService == null)
        {
            Log.e(BATTLE_PREPARE_TAG, "The player service not initialized yet!");
            return;
        }
        else if(playerService.GetCurrentUser() == null)
        {
            Log.e(BATTLE_PREPARE_TAG, "The current player information not initialized!");
            return;
        }

        finding = false;
        stating = false;

        FindBattle();
    }

    private void FindBattle()
    {
        if(!CheckCondition() || finding)
        {
            return;
        }

        finding = true;
        String userId = playerService.GetCurrentUser().getUserId();
        arenaService.FindBattle(userId, new IServerResponse<Enemy>() {
            @Override
            public void onSuccess(Enemy enemy) {
                User currentUser = playerService.GetCurrentUser();

                prepareView.SetEnemyAvatar(enemy.getAvatarUrl());
                prepareView.SetEnemyName(enemy.getName());
                prepareView.SetEnemyRankText(Common.GetMedalTitleFromLevel(enemy.getLevel()));
                prepareView.SetEnemyWinRate(Common.GetWinRate(enemy.getTotalMatch(), enemy.getWinMatch()));

                prepareView.SetUserAvatar(currentUser.getAvatarUrl());
                prepareView.SetUserName(currentUser.getName());
                prepareView.SetUserRankText(Common.GetMedalTitleFromLevel(currentUser.getLevel()));
                prepareView.SetUserWinRate(Common.GetWinRate(currentUser.getTotalMatch(), currentUser.getWinMatch()));

                Log.i(BATTLE_PREPARE_TAG, currentUser.getName());
                Log.i(BATTLE_PREPARE_TAG, currentUser.getAvatarUrl());
                Log.i(BATTLE_PREPARE_TAG, enemy.getName());
                Log.i(BATTLE_PREPARE_TAG, enemy.getAvatarUrl());

                finding = false;
            }

            @Override
            public void onError(SillyError error) {
                //Error message to user
                finding = false;
            }
        });
    }

    //Check basic condition
    private boolean CheckCondition()
    {
        if(arenaService == null || playerService == null || prepareView == null || playerService.GetCurrentUser() == null)
            return false;
        return  true;
    }
}
