package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View.ActivityMailBoxDetail;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter.IMailBoxPresenter;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter.MailBoxPresenter;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.Pattern.IAlertBoxResponse;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;
import khoavin.sillylearningenglish.SingleViewObject.Common;

public class MailActivity extends AppCompatActivity implements IMailBoxView {

    /**
     * The mailbox adapter
     */
    private MailBoxAdapter mailBoxAdapter;

    /**
     * The presenter
     */
    private IMailBoxPresenter mailBoxPresenter;

    /**
     * The RecyclerView view
     */
    @BindView(R.id.mailList)
    RecyclerView listMail;

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
     * Refresh one item on inbox
     *
     * @param refreshItem The refreshed item
     */
    @Override
    public void RefreshItem(Inbox refreshItem) {
        if (mailBoxAdapter != null) {
            int pos = mailBoxAdapter.getItemPosition(refreshItem);
            if (pos != -1) {
                //Do something
                mailBoxAdapter.updateDataSource(pos, refreshItem);
            }
        }
    }

    @Override
    public void ShowInformMessage(String message) {

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
}
