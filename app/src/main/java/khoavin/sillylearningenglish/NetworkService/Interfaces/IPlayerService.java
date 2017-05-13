package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;

public interface IPlayerService {
    /**
     * Get user information
     * @param user_id
     * The user's Identifier
     * @param user_name
     * /**
     * The user's name
     * @param user_avatar
     * /**
     * The user's avatar
     * @param receiver
     */
    void GetuserInformation(String user_id, String user_name, String user_avatar, Context context, IVolleyService volleyService, IVolleyResponse<User> receiver);

    /**
     * Get battle chains
     * @param user_id
     * The user's Identifier
     * @param receiver
     * The receiver to receive response from server
     */
    //void GetBattleChains(String user_id, Context context, IVolleyService volleyService, IVolleyResponse<User> receiver);

    /**
     * Get current user
     * @return
     * The current logged user
     */
    User GetCurrentUser();
}
