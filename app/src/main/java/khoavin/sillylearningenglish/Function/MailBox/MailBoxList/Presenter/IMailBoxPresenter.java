package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IMailBoxPresenter {

    void RefreshInboxItems(String user_name);
    void RemoveItem(String user_id, int mail_id );
    void SentItem(String sender_id, String receiver_id, String message);
}
