package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Leaderboards;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;

/**
 * Created by OatOal on 3/27/2017.
 */

public interface ILeaderboardService {

    /// <Sumary>
    /// Get global ranking
    /// </Sumary>
    void GetGlobalRanking(String user_id, IServerResponse<Leaderboards> receiver);

    /// <Sumary>
    /// Get friend ranking
    /// </Sumary>
    void GetFriendRanking(String user_id, IServerResponse<Leaderboards> receiver);

}
