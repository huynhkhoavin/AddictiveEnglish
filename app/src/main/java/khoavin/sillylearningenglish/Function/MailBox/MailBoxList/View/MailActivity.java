package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View.ActivityMailBoxDetail;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter.MailBoxPresenter;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;
import khoavin.sillylearningenglish.SingleViewObject.Common;

public class MailActivity extends AppCompatActivity implements IMailBoxView {
    private final String STATE_HAS_MAIL = "HasMail";
    private final String STATE_NO_MAIL = "NoMail";

    /**
     * The mailbox adapter
     */
    private MailBoxAdapter mailBoxAdapter;

    /**
     * The presenter
     */
    private MailBoxPresenter mailBoxPresenter;

    /**
     * The RecyclerView view
     */
    @BindView(R.id.mailList)
    RecyclerView listMail;

    /**
     * The empty indicator.
     * Display when inbox is empty
     */
    @BindView(R.id.empty_indicator)
    TextView emptyIndicator;

    /**
     * The inbox state
     */
    private UIView inboxState;

    /**
     * Initialize
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        ButterKnife.bind(this);
        setTitle(R.string.mail_title);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        //registry view state
        inboxState = new UIView();
        inboxState.RegistryState(STATE_HAS_MAIL, listMail);
        inboxState.RegistryState(STATE_NO_MAIL, emptyIndicator);

        //Initialize the mailbox presenter
        mailBoxPresenter = new MailBoxPresenter(this);


    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    /**
     * Show mail list
     *
     * @param items The Inbox data context
     */
    @Override
    public void ShowMailList(ArrayList<Inbox> items) {
        UpdateOrInitializeDataSource(items);
    }

    /**
     * Refresh all item on inbox
     *
     * @param newDataSource The new inbox data source
     */
    @Override
    public void RefreshAllItem(ArrayList<Inbox> newDataSource) {
        UpdateOrInitializeDataSource(newDataSource);
    }

    /**
     * Show empty indicator
     * @param flag: if flag is true, show indicator otherwise hide indicator
     */
    @Override
    public void ShowEmptyIndicator(boolean flag)
    {
        if(flag)
        {
            inboxState.ControlState(STATE_NO_MAIL);
        }
        else
        {
            inboxState.ControlState(STATE_HAS_MAIL);
        }
    }

    /**
     * Update or initialize data source for list view
     *
     * @param items
     */
    private void UpdateOrInitializeDataSource(ArrayList<Inbox> items) {
        if (mailBoxAdapter != null) {
            mailBoxAdapter.setDataSource(ArrayConvert.toObjectArray(items));
        } else {
            mailBoxAdapter = new MailBoxAdapter(this, ArrayConvert.toObjectArray(items));
            mailBoxAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
                @Override
                public void OnClick(int ItemPosition, Object ItemObject) {
                    Intent it = new Intent(getApplicationContext(), ActivityMailBoxDetail.class);
                    Inbox ibItem = (Inbox) ItemObject;
                    it.putExtra("INBOX_ITEM", ibItem);
                    startActivity(it);
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            listMail.setLayoutManager(linearLayoutManager);
            listMail.setAdapter(mailBoxAdapter);
            RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
            listMail.addItemDecoration(dividerItemDecoration);
        }
    }

    /**
     * Check for update of mailbox.
     */
    @Override
    public void onResume()
    {
        super.onResume();
        //mailBoxPresenter.CheckForRefreshInbox();
    }
}
