package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Model;

import java.util.ArrayList;

import khoavin.sillylearningenglish.SingleViewObject.Mail;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxModel implements IMailBoxModel {
    private ArrayList<Mail> mails= new ArrayList<Mail>();
    @Override
    public ArrayList<Mail> GetMailList() {
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",false));
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",true));
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",true));
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",true));
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",true));
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",true));
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",true));
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",true));
        mails.add(new Mail(false,"Hệ Thống","Thách đấu từ",true));
        return mails;
    }
}
