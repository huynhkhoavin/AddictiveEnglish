package khoavin.sillylearningenglish.Function.Arena.Views;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AppParam;
import khoavin.sillylearningenglish.SingleViewObject.Common;

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
    void SetUserRankMedal(int medalId);
    void SetEnemyRankMedal(int medalId);
    void PreparedSuccess();
    void PreparedFails();
    void SetButtonState(Common.BattleCalledFrom calledFrom);
    void SetBetValue(long bet_value);
    void SetMinBetValue(int minBetValue);


    String GetBetMoney();
    String GetMessage();
}
