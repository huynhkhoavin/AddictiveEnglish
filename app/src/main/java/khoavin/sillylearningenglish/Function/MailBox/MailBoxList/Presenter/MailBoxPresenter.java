package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter;

import android.app.Activity;
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
import khoavin.sillylearningenglish.Pattern.IAlertBoxResponse;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxPresenter implements IMailBoxPresenter {
    private IMailBoxView inboxView;

    private Activity GetView() {
        return (Activity) inboxView;
    }

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
     *
     * @param inboxView
     */
    public MailBoxPresenter(IMailBoxView inboxView) {
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
    private void InitializeInBoxView() {
        _lastFilterType = Common.FilterType.NEW;
        if (CheckCondition()) {
            inboxService.GetInboxItems(playerService.GetCurrentUser().getUserId(), GetView(), volleyService, new IVolleyResponse<ArrayList<Inbox>>() {
                @Override
                public void onSuccess(ArrayList<Inbox> items) {
                    if (items == null || items.size() <= 0) {
                        inboxView.ShowEmptyIndicator(true);
                    } else {
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

        inboxView.SetCheckerVisibleState(false);
    }

    /**
     * Called this function on resume activity.
     * Check for mail box refreshed.
     */
    public void CheckForRefreshInbox(Common.FilterType type) {
        if (type == Common.FilterType.NA) {

        } else {
            //Do something to refresh inbox.
            if (inboxService.IsInboxNeedUpdate()) {
                inboxService.SetInboxToUpToDate();
                inboxView.RefreshAllItem(inboxService.GetCurrentInboxItem(type));
                inboxView.ShowEmptyIndicator(inboxService.GetCurrentInboxItem(type).size() <= 0);
            }
        }
        inboxView.setSwipeRefreshingState();
    }

    /**
     * Check condition
     *
     * @return
     */
    private boolean CheckCondition() {
        if (this.playerService == null || this.volleyService == null || this.playerService.GetCurrentUser() == null)
            return false;
        return true;
    }

    /**
     * Update selected state for item.
     *
     * @param inbox     The mail item.
     * @param isChecked The checked state.
     */
    public void UpdateSelectedStateForItem(Inbox inbox, boolean isChecked) {
        inboxService.setInboxCheckedState(inbox.getId(), isChecked);
    }

    /**
     * Delete all checked mail.
     */
    public void DeleteAllCheckedMail() {
        Common.ShowConfirmMessage(GetView().getResources().getString(R.string.delete_confirm),
                GetView().getResources().getString(R.string.alert_title),
                GetView().getResources().getString(R.string.delete_button_title),
                GetView().getResources().getString(R.string.cancel_button_title)
                , GetView(),
                new IAlertBoxResponse() {
                    @Override
                    public void OnPositive() {
                        //Do something to delete all checked mail.
                        inboxService.RemoveSelectedMail(playerService.GetCurrentUser().getUserId(), GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
                            @Override
                            public void onSuccess(ErrorCode responseObj) {
                                CheckForRefreshInbox(_lastFilterType);
                            }

                            @Override
                            public void onError(ErrorCode errorCode) {
                                Toast.makeText(
                                        GetView(),
                                        String.format(GetView().getResources().getString(R.string.delete_selected_mail_error), errorCode.getCode().toString()),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void OnNegative() {

                    }
                });

    }

    /**
     * Check all mail
     */
    public void CheckedAllMail() {
        inboxService.CheckedAllMail(_lastFilterType);
        CheckForRefreshInbox(_lastFilterType);
    }

    /**
     * Un check all mail.
     */
    public void UnCheckAllMail() {
        inboxService.UnCheckAllMail();
        CheckForRefreshInbox(_lastFilterType);
    }


    /**
     * Save last filter type.
     */
    private Common.FilterType _lastFilterType;

    /**
     * Filter mail with type.
     *
     * @param filterType
     */
    public void FilterMail(Common.FilterType filterType) {
        if (filterType != _lastFilterType) {
            _lastFilterType = filterType;
            inboxView.RefreshAllItem(inboxService.GetCurrentInboxItem(_lastFilterType));
        }

    }

    private boolean currentShowedChecker = false;
    public void SetVisibleStateForCheckerItem() {
        currentShowedChecker = !currentShowedChecker;
        inboxView.SetCheckerVisibleState(currentShowedChecker);
        inboxService.SetCheckerVisibleState(currentShowedChecker);
        CheckForRefreshInbox(_lastFilterType);
    }

    public boolean getVisibleStateOfChecker(){
        return currentShowedChecker;
    }
}
