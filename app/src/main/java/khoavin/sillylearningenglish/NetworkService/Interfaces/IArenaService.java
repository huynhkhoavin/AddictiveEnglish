package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswer;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswers;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.functions.Func1;

public interface IArenaService {

    /**
     * Get current enemy
     *
     * @return
     */
    Enemy GetCurrentEnemy();

    /**
     * Get the current question list
     *
     * @return
     */
    Question[] GetCurrentQuestions();

    /**
     * Create battle
     *
     * @param user_id   The user's identifier
     * @param enemy_id  The Enemy identifier
     * @param bet_value The bet value
     * @param message   The Duel message
     */
    void CreateBattle(String user_id, String enemy_id, long bet_value, String message, Context context, final IVolleyService volleyService, IVolleyResponse<Question[]> receiver);

    /**
     * Find battle
     *
     * @param user_id The user's identifier
     */
    void FindBattle(String user_id, Context context, final IVolleyService volleyService, IVolleyResponse<Enemy> receiver);

    /**
     * Accept battle
     *
     * @param defender_id The user's identifier
     * @param battle_id   The battle identifier
     */
    void AcceptBattle(String defender_id, int battle_id, Context context, final IVolleyService volleyService, IVolleyResponse<Question[]> receiver);

    /**
     * @param user_id      The user's identifier
     * @param battle_id    The battle identifier
     * @param question_id  The question identifier
     * @param chose_answer The chose answer
     */
    void ChoseAnswer(String user_id, int battle_id, int question_id, int chose_answer, Context context, final IVolleyService volleyService, IVolleyResponse<AnswerChecker> receiver);

    /**
     * Get battle result
     *
     * @param user_id   The user's identifier
     * @param battle_id The battle's identifier
     */
    void GetBattleResult(String user_id, int battle_id, Context context, final IVolleyService volleyService, IVolleyResponse<MyAnswer[]> receiver);

    /**
     * Cancel battle
     *
     * @param user_id   The user's identifier
     * @param battle_id The battle's identifier
     */
    void CancelBattle(String user_id, int battle_id, Context context, IVolleyService volleyService, IVolleyResponse<ErrorCode> receiver);

    /**
     * Get the enemy duel
     *
     * @param user_id   The user's Identifier
     * @param battle_id The Battle Identifier
     */
    void GetEnemyDuel(String user_id, int battle_id, Context context, IVolleyService volleyService, IVolleyResponse<Enemy> receiver);

}
