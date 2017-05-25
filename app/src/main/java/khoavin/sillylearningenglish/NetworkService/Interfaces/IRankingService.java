package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Ranking;

/**
 * Created by OatOal on 5/24/2017.
 */

public interface IRankingService {

    /**
     * Gets the friend ranking.
     * @param userId The user's identifier.
     */
    void GetFriendRanking(String userId, Context context, IVolleyService volleyService, IVolleyResponse<ArrayList<Ranking>> volleyResponse);

    /**
     * Gets the global ranking.
     * @param userId The user's identifier.
     */
    void GetGlobalRanking(String userId, Context context, IVolleyService volleyService, IVolleyResponse<ArrayList<Ranking>> volleyResponse);


    /**
     * Add selected item to friend list.
     * @param user_id The current user identifier.
     * @param friend_id The user identifier will be add to current user friend list.
     */
    void AddFriend(String user_id, String friend_id, Context context, IVolleyService volleyService, IVolleyResponse<ErrorCode> volleyResponse);

    /**
     * Remove from friend list.
     * @param user_id
     * @param friend_id the friend.
     */
    void RemoveFriend(String user_id, String friend_id, Context context, IVolleyService volleyService, IVolleyResponse<ErrorCode> volleyResponse);

}
