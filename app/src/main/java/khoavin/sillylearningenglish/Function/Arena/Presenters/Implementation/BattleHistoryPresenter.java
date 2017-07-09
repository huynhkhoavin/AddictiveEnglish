package khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Presenters.IBattleHistoryPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.IBattleHistoryView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleHistory;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleHistoryInfo;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 6/20/2017.
 */

public class BattleHistoryPresenter implements IBattleHistoryPresenter {

    /**
     * The view.
     */
    IBattleHistoryView _theView;

    BaseDetailActivity GetView() {
        return (BaseDetailActivity) _theView;
    }

    /**
     * The arena service.
     */
    @Inject
    IArenaService arenaService;

    /**
     * The player service.
     */
    @Inject
    IPlayerService playerService;

    /**
     * The volley service.
     */
    @Inject
    IVolleyService volleyService;

    /**
     * The histories data.
     */
    private ArrayList<BattleHistory> _histories;

    /**
     * Initialize the history presenter.
     *
     * @param theView
     */
    public BattleHistoryPresenter(final IBattleHistoryView theView) {
        this._theView = theView;

        //Inject dependency.
        ((SillyApp) (GetView()).getApplication())
                .getDependencyComponent()
                .inject(this);

        InitialeOrRefreshHistory();
    }

    public void InitialeOrRefreshHistory(){
        if (playerService != null && arenaService != null && (_histories == null || _histories.size() == 0)) {
            arenaService.GetBattleHistory(playerService.GetCurrentUser().getUserId(), GetView(), volleyService, new IVolleyResponse<BattleHistoryInfo[]>() {
                @Override
                public void onSuccess(BattleHistoryInfo[] responseObj) {
                    if (responseObj != null && responseObj.length > 0)
                        HandleBattleHistoryInformation(responseObj);
                    _theView.SetBattleHistoryListView(_histories);
                    _theView.setSwipeState();
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    //Do something.
                    Log.e("BATTLE_HISTORY", errorCode.getDetails() + ", code: " + errorCode.getCode());
                    _theView.setSwipeState();
                }
            });
        } else {
            //Do something.
            Log.i("BATTLE_HISTORY", "missing condition to get battle histories or histories already!");
            _theView.setSwipeState();
        }
    }

    /**
     * Handle server response.
     *
     * @param info the battle history information.
     */
    private void HandleBattleHistoryInformation(BattleHistoryInfo[] info) {
        if (_histories == null && info != null && info.length > 0) {
            _histories = new ArrayList<BattleHistory>();
            long timeDiff = 0;
            for (int i = 0; i < info.length; i++) {
                BattleHistoryInfo historyInfo = info[i];
                BattleHistory battleHistory = new BattleHistory();
                if (historyInfo.getAttackerId().equals(playerService.GetCurrentUser().getUserId())) {
                    //current user is attacker.
                    battleHistory.setUserAvatar(historyInfo.getAttackerAvatar());
                    battleHistory.setUserName(historyInfo.getAttackerName());
                    battleHistory.setUserTrueAnswer(historyInfo.getTotalAttackerAnswer());

                    timeDiff = historyInfo.getAttackerEndTime().getTime() - historyInfo.getAttackerBeginTime().getTime();
                    battleHistory.setUserTotalTime(timeDiff);

                    //enemy is defender.
                    battleHistory.setEnemyAvatar(historyInfo.getDefenderAvatar());
                    battleHistory.setEnemyName(historyInfo.getDefenderName());
                    battleHistory.setEnemyTrueAnswer(historyInfo.getTotalDefenderAnswer());

                    timeDiff = historyInfo.getDefenderEndTime().getTime() - historyInfo.getDefenderBeginTime().getTime();
                    battleHistory.setEnemyTotalTime(timeDiff);

                    if (historyInfo.getAttackerId().equals(historyInfo.getVictoryId())) {
                        battleHistory.setVictoryBattle(true);
                    } else {
                        battleHistory.setVictoryBattle(false);
                    }
                    battleHistory.setDateCreate(historyInfo.getDateCreate());
                } else {
                    //enemy is attacker
                    battleHistory.setEnemyAvatar(historyInfo.getAttackerAvatar());
                    battleHistory.setEnemyName(historyInfo.getAttackerName());
                    battleHistory.setEnemyTrueAnswer(historyInfo.getTotalAttackerAnswer());

                    timeDiff = historyInfo.getAttackerEndTime().getTime() - historyInfo.getAttackerBeginTime().getTime();
                    battleHistory.setEnemyTotalTime(timeDiff);


                    //current user is defender.
                    battleHistory.setUserAvatar(historyInfo.getDefenderAvatar());
                    battleHistory.setUserName(historyInfo.getDefenderName());
                    battleHistory.setUserTrueAnswer(historyInfo.getTotalDefenderAnswer());

                    timeDiff = historyInfo.getDefenderEndTime().getTime() - historyInfo.getDefenderBeginTime().getTime();
                    battleHistory.setUserTotalTime(timeDiff);

                    if (historyInfo.getDefenderId().equals(historyInfo.getVictoryId())) {
                        battleHistory.setVictoryBattle(true);
                    } else {
                        battleHistory.setVictoryBattle(false);
                    }

                    battleHistory.setDateCreate(historyInfo.getDateCreate());
                }

                _histories.add(battleHistory);
            }
        }
    }
}
