package khoavin.sillylearningenglish.Function.Ranking.Views;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Ranking;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IRankingView {

    /**
     * Sets the user's name.
     * @param userName The user's name.
     */
    void setUserName(String userName);

    /**
     * Sets the user's rank position text.
     * @param rankPosition The user's rank position text.
     */
    void setUserRankPosition(String rankPosition);

    /**
     * Sets the user's win rate.
     * @param userWinRate The user's win rate text.
     */
    void setUserWinRate(String userWinRate);

    /**
     * Sets the user's total battle.
     * @param totalBattle
     */
    void setTotalBattle(String totalBattle);

    /**
     * Sets the user's medal
     * @param userMedal The drawable with level.
     */
    void setUserMedal(int userMedal);

    /**
     * Sets the rank title with user's level.
     * @param rankTitle The user's level title.
     */
    void setUserRankTitle(String rankTitle);

    /**
     * Sets the user's avatar.
     * @param avatarUrl The user's avatar url.
     */
    void setUserAvatar(String avatarUrl);

    /**
     * Set the add friend button state.
     * @param isUserFriend
     */
    void setAddFriendButtonState(boolean isUserFriend, boolean isUserItSelf);

    /**
     * Sets the global ranking data source.
     * @param items The global ranking data source.
     */
    void setGlobalDataSource(ArrayList<Ranking> items);

    /**
     * Sets the friend ranking data source
     * @param items The friend ranking source.
     */
    void setFriendDataSource(ArrayList<Ranking> items);

}
