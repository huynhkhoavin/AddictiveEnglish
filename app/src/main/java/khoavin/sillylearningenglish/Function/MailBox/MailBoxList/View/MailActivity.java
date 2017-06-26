package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterCheckboxClicked;
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
     * The mail filter spinner.
     */
    @BindView(R.id.mail_filter)
    Spinner mailFilterSpinner;

    /**
     * The mailbox toolbar.
     */
    @BindView(R.id.mail_toolbar)
    Toolbar toolbar;

    /**
     * The check all button.
     */
    @BindView(R.id.mail_check_all)
    CheckBox mailboxCheckAll;

    /**
     * The inbox state
     */
    private UIView inboxState;

    /**
     * Get the value indicate recycle view initialized state.
     */
    private boolean isInitializeRecycleView = false;

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

        //seting up toolbar
        setupToolBar();

        //registry view state
        inboxState = new UIView();
        inboxState.RegistryState(STATE_HAS_MAIL, listMail);
        inboxState.RegistryState(STATE_NO_MAIL, emptyIndicator);

        //Initialize the mailbox presenter
        mailBoxPresenter = new MailBoxPresenter(this);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete_selected:
                        mailBoxPresenter.DeleteAllCheckedMail();
                        break;
                }
                return false;
            }
        });

        mailboxCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mailBoxPresenter.CheckedAllMail();
                else
                    mailBoxPresenter.UnCheckAllMail();
            }
        });


    }

    /**
     * set menu_mail to actionbar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mail, menu);
        return true;
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
     *
     * @param flag: if flag is true, show indicator otherwise hide indicator
     */
    @Override
    public void ShowEmptyIndicator(boolean flag) {
        if (flag) {
            inboxState.ControlState(STATE_NO_MAIL);
        } else {
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

            mailBoxAdapter.SetAdapterCheckboxCheckedChange(new AdapterCheckboxClicked() {
                @Override
                public void onCheckboxCheckedChange(int position, Object object, boolean isChecked) {
                    mailBoxPresenter.UpdateSelectedStateForItem((Inbox) object, isChecked);
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            listMail.setLayoutManager(linearLayoutManager);
            listMail.setAdapter(mailBoxAdapter);
//            RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
//            listMail.addItemDecoration(dividerItemDecoration);
        }
    }

    /**
     * Check for update of mailbox.
     */
    @Override
    public void onResume() {
        super.onResume();
        mailBoxPresenter.CheckForRefreshInbox(Common.FilterType.NEW);
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mailFilterSpinner.setAdapter(new MailFilterAdapter(MailActivity.this, getResources().getStringArray(R.array.mail_filter_array)));
        mailFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mailBoxPresenter.FilterMail(Common.FilterType.fromInt(arg2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
}
