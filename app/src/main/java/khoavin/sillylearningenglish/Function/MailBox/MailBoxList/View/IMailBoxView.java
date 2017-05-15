package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;

public interface IMailBoxView {
    /**
     * Show mail list
     * @param mail_list
     * The mail list
     */
    void ShowMailList(ArrayList<Inbox> mail_list);

    /**
     * Notify view that user's inbox items has changed
     * @param newDataSource
     */
    void RefreshAllItem(ArrayList<Inbox> newDataSource);

    /**
     * Notify view that one inbox item has refreshed
     * @param refreshItem
     * The refreshed item
     */
    void RefreshItem(Inbox refreshItem);


    /**
     * Show inform message
     * @param message
     * The message
     */
    void ShowInformMessage(String message);
}
