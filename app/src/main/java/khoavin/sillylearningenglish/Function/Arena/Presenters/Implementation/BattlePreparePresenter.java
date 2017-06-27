package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IBattlePreparePresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IBattlePrepareView;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.MailActivity;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.Pattern.IAlertBoxResponse;
import khoavin.sillylearningenglish.R;
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
     * For battle called from inbox we have battle identifier.
     */
    private int battleIdentifier;
    private long battleBetValue;
    private int battleMailIdentifier;

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

    @Inject
    IInboxService inboxService;


    public BattlePreparePresenter(final IBattlePrepareView prepareView, int battleId, long battleBetValue, int battleMailIdentifier) {
        this.prepareView = prepareView;
        this.battleIdentifier = battleId;
        this.battleBetValue = battleBetValue;
        this.battleMailIdentifier = battleMailIdentifier;
        ((SillyApp) ((AppCompatActivity) this.prepareView).getApplication())
                .getDependencyComponent()
                .inject(this);

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

        prepareView.SetButtonState(arenaService.CalledBattleFrom());
        if (arenaService.CalledBattleFrom() == Common.BattleCalledFrom.FROM_INBOX) {
            if (arenaService.GetCurrentEnemy() != null &&
                    battleIdentifier != -1 &&
                    battleBetValue != -1 &&
                    battleMailIdentifier != -1) {
                EnemyToView(arenaService.GetCurrentEnemy());
                prepareView.SetBetValue(battleBetValue);
            } else {
                Common.ShowInformMessage(
                        GetView().getResources().getString(R.string.mail_missing_battle_info),
                        GetView().getResources().getString(R.string.alert_title),
                        GetView().getResources().getString(R.string.accept_message),
                        GetView(),
                        new IAlertBoxResponse() {
                            @Override
                            public void OnPositive() {
                                BackToInbox();
                            }

                            @Override
                            public void OnNegative() {

                            }
                        });
            }
        } else if (arenaService.CalledBattleFrom() == Common.BattleCalledFrom.FROM_RANKING) {
            finding = false;
            creatingBattle = false;
            EnemyToView(arenaService.GetCurrentEnemy());
        } else {
            finding = false;
            creatingBattle = false;
            FindBattle("");
        }
    }

    @Override
    public void FindOtherEnemy() {
        if(arenaService.GetCurrentEnemy() != null)
            FindBattle(arenaService.GetCurrentEnemy().getUserId());
        else
            FindBattle("");
    }

    @Override
    public void CreateBattle() {
        if (!CheckCondition()
                || creatingBattle
                || arenaService.GetCurrentEnemy() == null) {
            return;
        }

        String enemyId = arenaService.GetCurrentEnemy().getUserId();
        String userId = playerService.GetCurrentUser().getUserId();
        if (prepareView.GetBetMoney().equals("")) {
            Toast.makeText(
                    GetView().getBaseContext(),
                    GetView().getResources().getString(R.string.arena_fill_out_bet_value),
                    Toast.LENGTH_LONG).show();
        } else if (prepareView.GetMessage().equals("")) {
            Toast.makeText(
                    GetView().getBaseContext(),
                    GetView().getResources().getString(R.string.arena_fill_out_message),
                    Toast.LENGTH_LONG).show();
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

    @Override
    public void CancelBattle() {
        if (battleIdentifier != -1 && battleBetValue != -1 && battleMailIdentifier != -1) {
            arenaService.CancelBattle(playerService.GetCurrentUser().getUserId(), battleIdentifier, GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
                @Override
                public void onSuccess(ErrorCode responseObj) {
                    inboxService.RemoveMail(playerService.GetCurrentUser().getUserId(), battleMailIdentifier, GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
                        @Override
                        public void onSuccess(ErrorCode responseObj) {
                            BackToInbox();
                        }

                        @Override
                        public void onError(ErrorCode errorCode) {
                            Common.ShowInformMessage(
                                    String.format(GetView().getResources().getString(R.string.mail_delete_error), errorCode.getCode().toString()),
                                    GetView().getResources().getString(R.string.alert_title),
                                    GetView().getResources().getString(R.string.accept_message),
                                    GetView(),
                                    new IAlertBoxResponse() {
                                        @Override
                                        public void OnPositive() {
                                            BackToInbox();
                                        }

                                        @Override
                                        public void OnNegative() {

                                        }
                                    });
                        }
                    });
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    Common.ShowInformMessage(
                            String.format(GetView().getResources().getString(R.string.cancel_battle_error), errorCode.getCode().toString()),
                            GetView().getResources().getString(R.string.alert_title),
                            GetView().getResources().getString(R.string.accept_message),
                            GetView(),
                            new IAlertBoxResponse() {
                                @Override
                                public void OnPositive() {
                                    BackToInbox();
                                }

                                @Override
                                public void OnNegative() {

                                }
                            });
                }
            });
        }
    }

    @Override
    public void AcceptBattle() {
        if (battleIdentifier != -1 && battleBetValue != -1) {
            arenaService.AcceptBattle(playerService.GetCurrentUser().getUserId(), battleIdentifier, GetView(), volleyService, new IVolleyResponse<Question[]>() {
                @Override
                public void onSuccess(Question[] responseObj) {

                    //Set inbox to opened and answered mails.
                    inboxService.ClaimReward(playerService.GetCurrentUser().getUserId(), battleMailIdentifier, GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
                        @Override
                        public void onSuccess(ErrorCode responseObj) {

                        }

                        @Override
                        public void onError(ErrorCode errorCode) {

                        }
                    });

                    /**
                     * Show prepare info.
                     */
                    prepareView.PreparedSuccess();
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    prepareView.PreparedSuccess();
                }
            });
        }
    }

    /**
     * Find battle
     */
    private void FindBattle(String currentEnemyId) {
        if (!CheckCondition() || finding) {
            return;
        }

        if(currentEnemyId == null) currentEnemyId = "";

        finding = true;
        String userId = playerService.GetCurrentUser().getUserId();
        arenaService.FindBattle(userId, currentEnemyId, GetView(), volleyService, new IVolleyResponse<Enemy>() {
            @Override
            public void onSuccess(Enemy enemy) {
                EnemyToView(enemy);
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
     * Call this function to back to inbox.
     */
    private void BackToInbox() {
        Intent intent = new Intent(GetView(), MailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        GetView().startActivity(intent);
    }

    /**
     * Get prepare view as activity
     *
     * @return
     */
    private AppCompatActivity GetView() {
        return (AppCompatActivity) prepareView;
    }

    /**
     * Show enemy information to prepare.
     *
     * @param enemy
     */
    private void EnemyToView(Enemy enemy) {
        User currentUser = playerService.GetCurrentUser();
        prepareView.SetEnemyAvatar(enemy.getAvatarUrl());
        prepareView.SetEnemyName(enemy.getName());
        prepareView.SetEnemyRankText(Common.GetMedalTitleFromLevel(enemy.getLevel(), GetView().getBaseContext()));
        prepareView.SetEnemyWinRate(Common.GetWinRate(enemy.getTotalMatch(), enemy.getWinMatch()));
        prepareView.SetUserAvatar(currentUser.getAvatarUrl());
        prepareView.SetUserName(currentUser.getName());
        prepareView.SetUserRankText(Common.GetMedalTitleFromLevel(currentUser.getLevel(), GetView().getBaseContext()));
        prepareView.SetUserWinRate(Common.GetWinRate(currentUser.getTotalMatch(), currentUser.getWinMatch()));
        prepareView.SetUserRankMedal(Common.getRankMedalImage(currentUser.getLevel()));
        prepareView.SetEnemyRankMedal(Common.getRankMedalImage(enemy.getLevel()));

        Log.i(BATTLE_PREPARE_TAG, currentUser.getName());
        Log.i(BATTLE_PREPARE_TAG, currentUser.getAvatarUrl());
        Log.i(BATTLE_PREPARE_TAG, enemy.getName());
        Log.i(BATTLE_PREPARE_TAG, enemy.getAvatarUrl());
    }
}
