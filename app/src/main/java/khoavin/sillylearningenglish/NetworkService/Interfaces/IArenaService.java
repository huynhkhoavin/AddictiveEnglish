package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswers;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.functions.Func1;

public interface IArenaService {

    /// <summary>
    /// Server will create a battle and return question list
    /// </summary>
    void CreateBattle(String user_id, String enemy_id, IServerResponse<Questions> receiver);

    /// <summary>
    /// Find battle (Find random user)
    /// </summary>
    void FindBattle(String user_id, IServerResponse<Enemy> receiver);

    /// <summary>
    /// Accept battle from mail
    /// </summary>
    void AcceptBattle(String defender_id, int battle_id, IServerResponse<Questions> receiver);

    /// <summary>
    /// Sent chose answer to server
    /// </summary>
    void ChoseAnswer(String user_id, int battle_id, int question_id, int chose_answer, IServerResponse<AnswerChecker> receiver);

    /// <summary>
    /// Get battle result (list all user answer with true answer of the questions)
    /// </summary>
    void GetBattleResult(String user_id, int battle_id, IServerResponse<MyAnswers> receiver);

}
