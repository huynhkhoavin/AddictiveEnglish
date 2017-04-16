package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.IMailBoxView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inboxs;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxPresenter implements IMailBoxPresenter {
    private IMailBoxView inboxView;

    @Inject
    IPlayerService playerService;

    @Inject
    IInboxService inboxService;

    public MailBoxPresenter(IMailBoxView inboxView){
        this.inboxView = inboxView;

        //inject arena service
        ((SillyApp) ((AppCompatActivity) inboxView).getApplication())
                .getDependencyComponent()
                .inject(this);

        //Initialize inbox view
        InitializeInBoxView();
    }

    /**
     * Initialize inbox view
     * - Get inbox items from service
     * - Add inbox item to ScrollView on InboxView
     * - Response error if have
     */
    private void InitializeInBoxView()
    {
        if(CheckCondition())
        {
            inboxService.GetInboxItems(playerService.GetCurrentUser().getUserId(), new IServerResponse<Inboxs>() {
                @Override
                public void onSuccess(Inboxs items) {
                    inboxView.ShowMailList(items);
                }

                @Override
                public void onError(SillyError error) {

                }
            });
        }
    }

    private boolean CheckCondition()
    {
        if(this.playerService == null || this.inboxService == null || this.playerService.GetCurrentUser() == null)
            return false;
        return true;
    }

    @Override
    public void RefreshInboxItems(String user_name) {

    }

    @Override
    public void RemoveItem(String user_id, int mail_id) {

    }

    @Override
    public void SentItem(String sender_id, String receiver_id, String message) {

    }
}
