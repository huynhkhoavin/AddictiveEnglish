package khoavin.sillylearningenglish.Function.Arena.Views;

import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IArenaView {

    //Set user's current coins
    void SetCoins(Integer coin);

    //Set user's name
    void SetName(String name);

    //Set user's avatar url
    void SetAvatar(String avatar);

    //Set user's total battle
    void SetTotalBattle(Integer totalBattle);

    //Set battle chains
    void SetBattleChain(String battleChain);

    void SetRankMedal(int level);

    void SetRankTitle(int level);

    void SetWinBattle(int winBattle);

    void SetWinRateProgress(float rate);

}
