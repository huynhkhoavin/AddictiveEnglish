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

    //Set user's win rate
    void SetWinRate(String winRate);

    //Set level
    void SetLevel(Common.RankMedal rankMedal);

    //Set battle chains
    void SetBattleChain(String battleChain);

}
