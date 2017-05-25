package khoavin.sillylearningenglish.Function.Ranking.Presenter;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Ranking;

/**
 * Created by OatOal on 5/24/2017.
 */

public interface IRankingPresenter {

    /**
     * Sent add friend request.
     */
    void AddFriend();

    /**
     * Sent challenge request.
     */
    void Challenge();

    /**
     * Show details for item.
     * @param itemPosition The item position.
     */
    void ShowItem(int itemPosition, Ranking item, boolean isFriendRanking);

    /**
     * Show first friend ranking item.
     */
    void ShowFirstFriendRankingItem();

    /**
     * Show first global ranking item.
     */
    void ShowFirstGlobalRankingItem();

    /**
     * Gets and show global ranking.
     */
    void GetGlobalRanking();

    /**
     * Gets and show friend ranking.
     */
    void GetFriendRanking();

}
