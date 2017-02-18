package khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxList.Model;

import khoavin.sillylearningenglish.SINGLE_OBJECT.Mail;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxModel implements IMailBoxModel {
    private Mail[] mails= new Mail[]{
            new Mail(false,"Hệ Thống","Thách đấu từ",false),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true),
            new Mail(false,"Hệ Thống","Thách đấu từ",true)
    };
    @Override
    public Mail[] GetMailList() {
        return mails;
    }
}
