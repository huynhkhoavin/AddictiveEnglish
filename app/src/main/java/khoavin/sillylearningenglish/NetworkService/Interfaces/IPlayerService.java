package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.functions.Func1;

public interface IPlayerService {

    /// <Sumary>
    /// Get user information
    /// </Sumary>
    void GetuserInformation(String user_id, String user_name, String user_avatar, IServerResponse<User> receiver);

    /// <Sumary>
    /// Get battle chains
    /// </Sumary>
    void GetBattleChains(String user_id, IServerResponse<User> receiver);

    /// <Sumary>
    /// Get current user
    /// </Sumary>
    User GetCurrentUser();
}
