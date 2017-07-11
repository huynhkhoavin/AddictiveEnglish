package khoavin.sillylearningenglish.Function.Arena.Views.Implementation;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.BattleHistoryPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.BattleHistoryAdapter;
import khoavin.sillylearningenglish.Function.Arena.Views.IBattleHistoryView;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleHistory;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by OatOal on 6/20/2017.
 */

public class BattleHistoryActivity extends BaseDetailActivity implements IBattleHistoryView {

    private final String STATE_HISTORY = "has_data";
    private final String STATE_NONE_HISTORY = "no_data";
    private UIView viewState;

    /**
     * The battle history list view.
     */
    @BindView(R.id.battle_history_list_view)
    RecyclerView historyListView;

    /**
     * The empty indicator.
     */
    @BindView(R.id.battle_history_state_empty)
    TextView emptyIndicator;

    @BindView(R.id.titleBar)
    LinearLayout titleBar;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * The battle history adapter.
     */
    BattleHistoryAdapter battleHistoryAdapter;

    /**
     * The battle histories presenter.
     */
    BattleHistoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_history);
        ButterKnife.bind(this);
        setupWindowAnimations();
        setTitle(getResources().getString(R.string.ranking_view_title));
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        viewState = new UIView();
        viewState.RegistryState(STATE_HISTORY, historyListView);
        viewState.RegistryState(STATE_NONE_HISTORY, emptyIndicator);

        presenter = new BattleHistoryPresenter(this);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.InitializeOrRefreshHistory();
            }
        });
    }

    /**
     * Initialize or refresh the list view.
     *
     * @param items
     */
    private void InitializeOrRefreshListView(ArrayList<BattleHistory> items) {

        //Enable or disable the list view.
        if (items == null || items.size() <= 0) {
            viewState.ActiveControl(STATE_NONE_HISTORY);
        } else {
            viewState.ActiveControl(STATE_HISTORY);
        }

        if (battleHistoryAdapter == null) {
            battleHistoryAdapter = new BattleHistoryAdapter(getBaseContext(), ArrayConvert.toObjectArray(items));
            battleHistoryAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
                @Override
                public void OnClick(int ItemPosition, Object ItemObject) {
                    //Do something when selected item.
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            historyListView.setLayoutManager(linearLayoutManager);
            historyListView.setAdapter(battleHistoryAdapter);
        } else {
            battleHistoryAdapter.setDataSource(ArrayConvert.toObjectArray(items));
        }
    }

    /**
     * Set battle histories to list view.
     *
     * @param battleHistories The battles histories.
     */
    @Override
    public void SetBattleHistoryListView(ArrayList<BattleHistory> battleHistories) {
        InitializeOrRefreshListView(battleHistories);
    }

    @Override
    public void setSwipeState() {
        swipeRefreshLayout.setRefreshing(false);
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
