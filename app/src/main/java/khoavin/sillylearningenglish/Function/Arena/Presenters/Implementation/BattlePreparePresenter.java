package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IBattlePreparePresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IBattlePrepareView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.SingleViewObject.Common;

public class BattlePreparePresenter implements IBattlePreparePresenter {

    /**
     * The log tag
     */
    private static final String BATTLE_PREPARE_TAG = "PREPARE_PRESENTER: ";

    /**
     * The view
     */
    private IBattlePrepareView prepareView;

    /**
     * The finding battle flag
     */
    private boolean finding, creatingBattle;

    /**
     * Inject player service
     */
    @Inject
    IPlayerService playerService;

    /**
     * Inject volley service
     */
    @Inject
    IVolleyService volleyService;

    @Inject
    IArenaService arenaService;


    public BattlePreparePresenter(final IBattlePrepareView prepareView) {
        this.prepareView = prepareView;
        ((SillyApp) ((AppCompatActivity) this.prepareView).getApplication())
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
        if (!CheckCondition()
                || creatingBattle
                || arenaService.GetCurrentEnemy() == null) {
            return;
        }

        String enemyId = arenaService.GetCurrentEnemy().getUserId();
        String userId = playerService.GetCurrentUser().getUserId();
        if (prepareView.GetBetMoney().equals("")) {
            Toast.makeText(GetView().getBaseContext(), "Không được bỏ trống giá trị tiền cược", Toast.LENGTH_LONG).show();
        } else if (prepareView.GetMessage().equals("")) {
            Toast.makeText(GetView().getBaseContext(), "Không được bỏ trống thông điệp thách đấu", Toast.LENGTH_LONG).show();
        } else {
            creatingBattle = true;
            arenaService.CreateBattle(
                    userId,
                    enemyId,
                    Integer.valueOf(prepareView.GetBetMoney()),
                    prepareView.GetMessage(), GetView(), volleyService,
                    new IVolleyResponse<Question[]>() {
                        @Override
                        public void onSuccess(Question[] responseObj) {
                            prepareView.PreparedSuccess();
                            creatingBattle = false;
                        }

                        @Override
                        public void onError(ErrorCode errorCode) {
                            prepareView.PreparedFails();
                            creatingBattle = false;
                        }
                    });
        }
    }

    /**
     * Initialize the view
     */
    private void InitView() {
        if (arenaService == null) {
            Log.e(BATTLE_PREPARE_TAG, "The arena service not initialized yet!");
            return;
        }

        if (playerService == null) {
            Log.e(BATTLE_PREPARE_TAG, "The player service not initialized yet!");
            return;
        } else if (playerService.GetCurrentUser() == null) {
            Log.e(BATTLE_PREPARE_TAG, "The current player information not initialized!");
            return;
        }

        finding = false;
        creatingBattle = false;

        FindBattle();
    }

    /**
     * Find battle
     */
    private void FindBattle() {
        if (!CheckCondition() || finding) {
            return;
        }

        finding = true;
        String userId = playerService.GetCurrentUser().getUserId();
        arenaService.FindBattle(userId, GetView(), volleyService, new IVolleyResponse<Enemy>() {
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
            public void onError(ErrorCode errorCode) {
                finding = false;
            }
        });
    }

    //Check basic condition
    private boolean CheckCondition() {
        if (arenaService == null || playerService == null || prepareView == null || playerService.GetCurrentUser() == null)
            return false;
        return true;
    }

    /**
     * Get prepare view as activity
     *
     * @return
     */
    private AppCompatActivity GetView() {
        return (AppCompatActivity) prepareView;
    }
}
