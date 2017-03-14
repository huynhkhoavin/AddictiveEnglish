package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import java.util.ArrayList;

import khoavin.sillylearningenglish.SingleViewObject.Mail;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IMailBoxView {
    void ShowMailList(ArrayList<Mail> mails);
    void ShowMailDetail(int position);
}
