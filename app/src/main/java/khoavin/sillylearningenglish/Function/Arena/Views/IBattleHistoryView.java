package khoavin.sillylearningenglish.Function.Arena.Views;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleHistory;

/**
 * Created by OatOal on 6/20/2017.
 */

public interface IBattleHistoryView {
    /**
     * Set the battles history.
     * @param battleHistories
     */
    void SetBattleHistoryListView(ArrayList<BattleHistory> battleHistories);

    void setSwipeState();
}
