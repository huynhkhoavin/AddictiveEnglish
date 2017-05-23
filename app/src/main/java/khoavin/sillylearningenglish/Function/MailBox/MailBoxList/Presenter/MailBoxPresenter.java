package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.ArrayList;
import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.IMailBoxView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxPresenter implements IMailBoxPresenter {
    private IMailBoxView inboxView;
    private AppCompatActivity GetView(){return (AppCompatActivity)inboxView;}

    /**
     * Inject the player service
     */
    @Inject
    IPlayerService playerService;

    /**
     * Inject the volley service
     */
    @Inject
    IVolleyService volleyService;

    /**
     * Inject inbox service
     */
    @Inject
    IInboxService inboxService;


    /**
     * Initialize the MailBoxPresenter
     * @param inboxView
     */
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
            inboxService.GetInboxItems(playerService.GetCurrentUser().getUserId(), GetView(), volleyService, new IVolleyResponse<ArrayList<Inbox>>() {
                @Override
                public void onSuccess(ArrayList<Inbox> items) {
                    if(items == null || items.size() <= 0)
                    {
                        inboxView.ShowEmptyIndicator(true);
                    }
                    else
                    {
                        inboxView.ShowEmptyIndicator(false);
                        inboxView.ShowMailList(items);
                    }
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    Toast.makeText(GetView().getBaseContext(), errorCode.getDetails(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Called this function on resume activity.
     * Check for mail box refreshed.
     */
    public void CheckForRefreshInbox()
    {
        //Do something to refresh inbox.
        if(inboxService.IsInboxNeedUpdate())
        {
            inboxService.SetInboxToUpToDate();
            inboxView.RefreshAllItem(inboxService.GetCurrentInboxItem());
        }
    }

    /**
     * Check condition
     * @return
     */
    private boolean CheckCondition()
    {
        if(this.playerService == null || this.volleyService == null || this.playerService.GetCurrentUser() == null)
            return false;
        return true;
    }
}
