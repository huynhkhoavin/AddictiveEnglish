package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inboxs;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IMailBoxView {
    void ShowMailList(Inboxs inboxs);
    void ShowMailList(Inbox[] inboxs);
    void ShowMailDetail(int position);
}
