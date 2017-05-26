package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IMailBoxDetailPresenter {
    /**
     * Set presenter's datacontext
     *
     * @param dataContext
     */
    void SetDataContext(Inbox dataContext);

    /**
     * Ratting this mail
     */
    void RattingMail();

    /**
     * Delete this mail
     */
    void DeleteMail();

    /**
     * Back to inbox view
     */
    void BackToInbox();

    /**
     * Accept battle
     */
    void AcceptBattle();

    /**
     * Cancel battle
     */
    void CancelBattle();

    /**
     * Claim reward
     */
    void ClaimReward();

    /**
     * Accept friend request.
     */
    void AnswerFriendRequest();

    /**
     * Cancel friend request.
     */
    void CancelFriendRequest();
}
