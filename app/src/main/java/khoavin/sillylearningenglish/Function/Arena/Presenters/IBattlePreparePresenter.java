package khoavin.sillylearningenglish.Function.Arena.Presenters;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IBattlePreparePresenter {

    /**
     * Find other enemy to fight.
     */
    void FindOtherEnemy();

    /**
     * Create battle.
     */
    void CreateBattle();

    /**
     * Call cancel battle. this battle called from inbox.
     */
    void CancelBattle();

    /**
     * Accept battle.
     */
    void AcceptBattle();

}
