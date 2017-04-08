package khoavin.sillylearningenglish.Function.Arena.Views;

import rx.functions.Func0;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IBattlePrepareView {

    void SetUserName(String userName);
    void SetEnemyName(String enemyName);
    void SetUserAvatar(String userAvater);
    void SetEnemyAvatar(String enemyAvatar);
    void SetUserWinRate(String userWinRate);
    void SetEnemyWinRate(String enemyWinRate);
    void SetUserRankText(String userRankText);
    void SetEnemyRankText(String enemyRankText);
    void PreparedSuccess();
    void PreparedFails();


    String GetBetMoney();
    String GetMessage();
}
