package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Visibility;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterCheckboxClicked;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemLongClick;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View.ActivityMailBoxDetail;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter.MailBoxPresenter;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;
import khoavin.sillylearningenglish.SingleViewObject.Common;

public class MailActivity extends BaseDetailActivity implements IMailBoxView {
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

    @BindView(R.id.delete_mail_button)
    ImageView mailDeleteButton;

    @BindView(R.id.selected_tool_bar)
    RelativeLayout selectedToolBar;

    @BindView(R.id.show_filter_button)
    ImageView showFilterButton;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

//    /**
//     * The mailbox toolbar.
//     */
//    @BindView(R.id.mail_toolbar)
//    Toolbar toolbar;

    /**
     * The check all button.
     */
    @BindView(R.id.mail_check_all)
    CheckBox mailboxCheckAll;

    @BindView(R.id.titleBar)
    LinearLayout titleBar;

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
        setupWindowAnimations();
        setTitle(R.string.mail_title);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        //seting up toolbar
        setupToolBar();

        //registry view state
        inboxState = new UIView();
        inboxState.RegistryState(STATE_HAS_MAIL, listMail);
        inboxState.RegistryState(STATE_NO_MAIL, emptyIndicator);

        //Initialize the mailbox presenter
        mailBoxPresenter = new MailBoxPresenter(this);

        mailDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailBoxPresenter.DeleteAllCheckedMail();
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mailBoxPresenter.CheckForRefreshInbox(Common.FilterType.NEW);
            }
        });


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

    @Override
    public void SetCheckerVisibleState(boolean state) {
        mailboxCheckAll.setChecked(false);
        if (state)
        {
            selectedToolBar.setVisibility(View.VISIBLE);
            mailboxCheckAll.setVisibility(View.VISIBLE);
        }
        else
        {
            selectedToolBar.setVisibility(View.GONE);
            mailboxCheckAll.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSwipeRefreshingState() {
        swipeRefreshLayout.setRefreshing(false);
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
                    transitionTo(it);
                }
            });

            mailBoxAdapter.SetAdapterCheckboxCheckedChange(new AdapterCheckboxClicked() {
                @Override
                public void onCheckboxCheckedChange(int position, Object object, boolean isChecked) {
                    mailBoxPresenter.UpdateSelectedStateForItem((Inbox) object, isChecked);
                }
            });

            mailBoxAdapter.setOnItemLongClick(new AdapterOnItemLongClick() {
                @Override
                public void OnClick(int ItemPosition, Object ItemObject) {
                    mailBoxPresenter.SetVisibleStateForCheckerItem();
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            listMail.setLayoutManager(linearLayoutManager);
            listMail.setAdapter(mailBoxAdapter);
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

    @Override
    public void onBackPressed() {
        if (mailBoxPresenter.getVisibleStateOfChecker()) {
            mailBoxPresenter.SetVisibleStateForCheckerItem();
        } else {
            super.onBackPressed();
        }
    }

    private void setupToolBar() {
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

        showFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailFilterSpinner.performClick();
            }
        });
    }

    //region transition
    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }

    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        // This view will not be affected by enter transition animation
        //enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        return enterTransition;
    }
    //endregion
}
