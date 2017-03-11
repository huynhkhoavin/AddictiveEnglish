package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter;

import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Model.IMailBoxModel;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.IMailBoxView;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxPresenter implements IMailBoxPresenter {
    private IMailBoxModel mailBoxModel;
    private IMailBoxView mailBoxView;

    public MailBoxPresenter(IMailBoxView mbv, IMailBoxModel mbm){
        this.mailBoxModel = mbm;
        this.mailBoxView = mbv;
    }
    @Override
    public void ShowMailList() {
        mailBoxView.ShowMailList(mailBoxModel.GetMailList());
    }

    @Override
    public void ShowMailDetail(int position) {

    }
}
