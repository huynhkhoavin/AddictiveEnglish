package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleQuestions;
import rx.functions.Func1;

public interface IArenaService {

    /// <summary>
    /// Server will create a battle and return question list
    /// </summary>
    void CreateBattle(String user_id, String enemy_id, final Func1<BattleQuestions, Void> receiver);

    /// <summary>
    /// Sent complete request to server and receive result of this battle
    /// </summary>
    void GetBattleResult(String battle_id, String user_id);
}
