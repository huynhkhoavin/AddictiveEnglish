package khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxList.View;

import khoavin.sillylearningenglish.SINGLE_OBJECT.Mail;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IMailBoxView {
    void ShowMailList(Mail[] mails);
    void ShowMailDetail(int position);
}