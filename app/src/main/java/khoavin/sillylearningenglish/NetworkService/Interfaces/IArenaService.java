package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswer;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswers;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.functions.Func1;

public interface IArenaService {

    /// <sumary>
    /// Get current enemy
    /// </sumary>
    Enemy GetCurrentEnemy();

    ///The questions
    Question[] GetCurrentQuestions();

    /// <summary>
    /// Server will create a battle and return question list
    /// </summary>
    void CreateBattle(String user_id, String enemy_id, long bet_value, String message, Context context, final IVolleyService volleyService, IVolleyResponse<Question[]> receiver);

    /// <summary>
    /// Find battle (Find random user)
    /// </summary>
    void FindBattle(String user_id, Context context, final IVolleyService volleyService, IVolleyResponse<Enemy> receiver);

    /// <summary>
    /// Accept battle from mail
    /// </summary>
    void AcceptBattle(String defender_id, int battle_id, Context context, final IVolleyService volleyService, IVolleyResponse<Question[]> receiver);

    /// <summary>
    /// Sent chose answer to server
    /// </summary>
    void ChoseAnswer(String user_id, int battle_id, int question_id, int chose_answer, Context context, final IVolleyService volleyService, IVolleyResponse<AnswerChecker> receiver);

    /// <summary>
    /// Get battle result (list all user answer with true answer of the questions)
    /// </summary>
    void GetBattleResult(String user_id, int battle_id,  Context context, final IVolleyService volleyService, IVolleyResponse<MyAnswer[]> receiver);

    /**
     * Get the enemy duel
     * @param user_id
     * The user's Identifier
     * @param battle_id
     * The Battle Identifier
     */
    void GetEnemyDuel(String user_id, int battle_id, Context context, IVolleyService volleyService, IVolleyResponse<Enemy> receiver);

}
