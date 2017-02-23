package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleInformation;

public interface IArenaService {

    /// <summary>
    /// Server will create a battle and return question list
    /// </summary>
    void CreateBattle(String user_id, String enemy_id, final IServerResponse<BattleInformation> serverResponse);

    /// <summary>
    /// Sent complete request to server and receive result of this battle
    /// </summary>
    void GetBattleResult(String battle_id, String user_id);
}
