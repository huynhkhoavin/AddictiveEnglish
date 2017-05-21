package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View;

import java.util.ArrayList;
import java.util.Date;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.AttachItem;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IMailBoxDetailView
{
    /**
     * Set mail tite
     * @param title
     * The mail title
     */
    void SetTitle(String title);

    /**
     * Set mail status
     * @param status
     * The mail status
     */
    void SetStatus(String status);

    /**
     * Set mail sent time
     * @param time
     */
    void SetTime(Date time);

    /**
     * Set message
     * @param message
     * The message
     */
    void SetMessage(String message);

    /**
     * Set banner image
     * @param url
     * the banner url
     */
    void SetImage(String url);

    /**
     * Set banner image
     * @param id
     * The banner id
     */
    void SetImage(int id);

    /**
     * Set coins
     * @param coins
     * Coins number
     */
    void SetCoins(int coins);

    /**
     * Set book name
     * @param bookName
     * The book's name
     */
    void SetBookName(String bookName);

    /**
     * Ratting or not (mask as important mail)
     * @param key
     * The key
     * + true: mask as important mail
     * + false: not important mail
     */
    void RattingMail(boolean key);

    /**
     * Set the up - down rank
     * @param newRank
     * The new rank
     */
    void SetUpDownRank(String newRank);

    /**
     * Set ratting state
     * @param isRated
     * The rating state
     */
    void SetRatingState(boolean isRated);

    /**
     * Set button state with mail type.
     * @param type The mail type.
     */
    void SetButtonState(Common.MailType type);

    /**
     * Set the attach items state.
     * @param attachItems
     */
    void SetItemState(ArrayList<AttachItem> attachItems);
}
