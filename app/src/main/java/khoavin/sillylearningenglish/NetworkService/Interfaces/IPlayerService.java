package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.app.Activity;
import android.content.Context;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AppParam;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.SingleViewObject.Common;

public interface IPlayerService {
    /**
     * Get user information
     *
     * @param user_id     The user's Identifier
     * @param user_name   The user's name
     * @param user_avatar The user's avatar
     */
    void GetuserInformation(String user_id, String user_name, String user_avatar, Activity activity, IVolleyResponse<User> receiver);

    /**
     * Get app parameters
     * @param user_id The user's identifier.
     */
    void GetAppParams(String user_id, Activity context, IVolleyResponse<AppParam[]> receiver);

//    /**
//     * Get battle chains
//     *
//     * @param user_id The user's Identifier
//     */
//    void GetBattleChains(String user_id, Context context, IVolleyService volleyService, IVolleyResponse<User> receiver);

    /**
     * Get current user
     *
     * @return The current logged user
     */
    User GetCurrentUser();

    /**
     * Get app params.
     * @param type The params type.
     * @return The AppParam
     */
    AppParam GetAppParams(Common.ParamType type);
}
