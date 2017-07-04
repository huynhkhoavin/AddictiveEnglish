package khoavin.sillylearningenglish.Function.Ranking.Views;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.Ranking.Presenter.IRankingPresenter;
import khoavin.sillylearningenglish.Function.Ranking.Presenter.RankingPresenter;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Ranking;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;

/**
 * Created by OatOal on 2/18/2017.
 */

public class RankingActivity extends AppCompatActivity implements IRankingView {

    //region Properties

    /**
     * The list view state.
     */
    private final String STATE_LIST_VIEW_GLOBAL_RANKING = "ListViewGlobal";
    private final String STATE_LIST_VIEW_FRIEND_RANKING = "ListViewFriend";

    /**
     * The button state.
     */
    private final String STATE_BUTTON_GLOBAL_RANKING_SELECTED = "ButtonGlobalActive";
    private final String STATE_BUTTON_GLOBAL_RANKING_NONE_SELECTED = "ButtonGlobalInActive";
    private final String STATE_BUTTON_FRIEND_RANKING_SELECTED = "ButtonFriendActive";
    private final String STATE_BUTTON_FRIEND_RANKING_NONE_SELECTED = "ButtonFriendInActive";

    private final String STATE_BUTTON_ADD_FRIEND_ACTIVE = "ButtonAddFriendActive";
    private final String STATE_BUTTON_ADD_FRIEND_IN_ACTIVE = "ButtonAddFriendInActive";
    private final String STATE_BUTTON_CHALLENGE_ACTIVE = "ButtonChallengeActive";
    private final String STATE_BUTTON_CHALLENGE_IN_ACTIVE = "ButtonChallengeInActive";

    /**
     * The selected user's rank position.
     */
    @BindView(R.id.ranking_view_rank_position)
    TextView rankPosition;

    /**
     * The selected user's avatar.
     */
    @BindView(R.id.ranking_view_user_avatar)
    ImageView userAvatar;

    /**
     * The selected user's name.
     */
    @BindView(R.id.ranking_view_user_name)
    TextView userName;

    /**
     * The selected user's win rate.
     */
    @BindView(R.id.ranking_view_win_rate)
    TextView userWinRate;

    /**
     * The selected user's total battle.
     */
    @BindView(R.id.ranking_view_total_battle)
    TextView userTotalBattle;

    /**
     * The selected user's medal.
     */
    @BindView(R.id.ranking_view_user_medal)
    ImageView userRankMedal;

    /**
     * The selected user's level.
     */
    @BindView(R.id.ranking_view_user_rank)
    TextView userRankTitle;

    /**
     * The add friend button.
     */
    @BindView(R.id.ranking_view_button_add_friend)
    Button buttonAddFriend;

    /**
     * The add friend button state inactive
     */
    @BindView(R.id.ranking_view_button_add_friend_state_de_active)
    Button buttonAddFriendStateDeActive;

    /**
     * The challenge button.
     */
    @BindView(R.id.ranking_view_button_challenge)
    Button buttonChallenge;

    /**
     * The challenge button inactive state.
     */
    @BindView(R.id.ranking_view_button_challenge_disable)
    Button getButtonChallengeInactive;

    /**
     * The button to show global ranking.
     */
    @BindView(R.id.ranking_view_button_global_none_selected)
    Button buttonGlobalRankingNoneSelected;

    /**
     * The button to show friend ranking.
     */
    @BindView(R.id.ranking_view_button_friend_none_selected)
    Button buttonFriendRankingNoneSelected;

    /**
     * The global recycler view.
     */
    @BindView(R.id.ranking_view_global_rank_list_view)
    RecyclerView recyclerViewGlobal;

    /**
     * The friend recycler view.
     */
    @BindView(R.id.ranking_view_friend_rank_list_view)
    RecyclerView recyclerViewFriend;

    /**
     * The button to show global ranking. clickable is false.
     */
    @BindView(R.id.ranking_view_button_global_selected)
    Button buttonGlobalRankingSelected;

    /**
     * The button to show friend ranking. clickable is false.
     */
    @BindView(R.id.ranking_view_button_friend_selected)
    Button buttonFriendRankingSelected;

    /**
     * The friend ranking adapter.
     */
    private RankingAdapter friendRankingAdapter;

    /**
     * The global ranking adapter.
     */
    private RankingAdapter globalRankingAdapter;

    /**
     * The list view state manager.
     */
    private UIView listViewState;

    /**
     * Tab state manager.
     */
    private UIView tabState;

    /**
     * The add friend button state.
     */
    private UIView addFriendButtonState;

    /**
     * The ranking presenter.
     */
    private IRankingPresenter presenter;

    //endregion

    @Override
    public void onBackPressed() {
        finish();
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_view);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.ranking_view_title));
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        //Register ui state.
        RegisterUIView();

        //Register click event.
        RegisterEvent();

        //Register default state
        listViewState.ControlState(STATE_LIST_VIEW_GLOBAL_RANKING);
        tabState.DeactiveAllcontrol();
        tabState.ActiveControl(STATE_BUTTON_FRIEND_RANKING_NONE_SELECTED);
        tabState.ActiveControl(STATE_BUTTON_GLOBAL_RANKING_SELECTED);

        //Initialize the ranking presenter.
        presenter = new RankingPresenter(this);

    }

    /**
     * Register UI View.
     */
    private void RegisterUIView() {
        listViewState = new UIView();
        listViewState.RegistryState(STATE_LIST_VIEW_FRIEND_RANKING, recyclerViewFriend);
        listViewState.RegistryState(STATE_LIST_VIEW_GLOBAL_RANKING, recyclerViewGlobal);

        tabState = new UIView();
        tabState.RegistryState(STATE_BUTTON_FRIEND_RANKING_SELECTED, buttonFriendRankingSelected);
        tabState.RegistryState(STATE_BUTTON_GLOBAL_RANKING_SELECTED, buttonGlobalRankingSelected);
        tabState.RegistryState(STATE_BUTTON_FRIEND_RANKING_NONE_SELECTED, buttonFriendRankingNoneSelected);
        tabState.RegistryState(STATE_BUTTON_GLOBAL_RANKING_NONE_SELECTED, buttonGlobalRankingNoneSelected);

        addFriendButtonState = new UIView();
        addFriendButtonState.RegistryState(STATE_BUTTON_ADD_FRIEND_IN_ACTIVE, buttonAddFriendStateDeActive);
        addFriendButtonState.RegistryState(STATE_BUTTON_ADD_FRIEND_ACTIVE, buttonAddFriend);
        addFriendButtonState.RegistryState(STATE_BUTTON_CHALLENGE_ACTIVE, buttonChallenge);
        addFriendButtonState.RegistryState(STATE_BUTTON_CHALLENGE_IN_ACTIVE, getButtonChallengeInactive);


    }

    /**
     * Register the onClick event for all button.
     * Manager tab state.
     */
    private void RegisterEvent() {
        buttonFriendRankingNoneSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enable friend tab and disable global tab.
                listViewState.ControlState(STATE_LIST_VIEW_FRIEND_RANKING);
                tabState.DeactiveAllcontrol();
                tabState.ActiveControl(STATE_BUTTON_FRIEND_RANKING_SELECTED);
                tabState.ActiveControl(STATE_BUTTON_GLOBAL_RANKING_NONE_SELECTED);

                //Gets and show friend ranking.
                presenter.GetFriendRanking();
            }
        });

        buttonGlobalRankingNoneSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Disable friend tab and enable global tab.
                listViewState.ControlState(STATE_LIST_VIEW_GLOBAL_RANKING);
                tabState.DeactiveAllcontrol();
                tabState.ActiveControl(STATE_BUTTON_FRIEND_RANKING_NONE_SELECTED);
                tabState.ActiveControl(STATE_BUTTON_GLOBAL_RANKING_SELECTED);

                //Gets and show global ranking.
                presenter.GetGlobalRanking();
            }
        });

        buttonAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.AddFriend();
            }
        });

        buttonChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.Challenge();
            }
        });
    }

    //region Implementations
    @Override
    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    @Override
    public void setUserRankPosition(String rankPosition) {
        this.rankPosition.setText(rankPosition);
    }

    @Override
    public void setUserWinRate(String userWinRate) {
        this.userWinRate.setText(userWinRate);
    }

    @Override
    public void setTotalBattle(String totalBattle) {
        this.userTotalBattle.setText(totalBattle);
    }

    @Override
    public void setUserMedal(int userMedal) {
        this.userRankMedal.setImageResource(userMedal);
    }

    @Override
    public void setUserRankTitle(String rankTitle) {
        this.userRankTitle.setText(rankTitle);
    }

    @Override
    public void setUserAvatar(String avatarUrl) {
        Glide.with(getBaseContext())
                .load(avatarUrl)
                .into(userAvatar);
    }

    @Override
    public void setAddFriendButtonState(boolean isUserFriend, boolean isUserItSelf) {
        addFriendButtonState.DeactiveAllcontrol();
        if(isUserFriend)
        {
            addFriendButtonState.ActiveControl(STATE_BUTTON_ADD_FRIEND_IN_ACTIVE);
        }
        else
        {
            addFriendButtonState.ActiveControl(STATE_BUTTON_ADD_FRIEND_ACTIVE);
        }

        if(isUserItSelf)
        {
            addFriendButtonState.ActiveControl(STATE_BUTTON_CHALLENGE_IN_ACTIVE);
        }
        else
        {
            addFriendButtonState.ActiveControl(STATE_BUTTON_CHALLENGE_ACTIVE);
        }
    }

    @Override
    public void setGlobalDataSource(ArrayList<Ranking> items) {
        if (globalRankingAdapter == null) {
            globalRankingAdapter = new RankingAdapter(this, ArrayConvert.toObjectArray(items));
            globalRankingAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
                @Override
                public void OnClick(int ItemPosition, Object ItemObject) {
                    presenter.ShowItem(ItemPosition, (Ranking) ItemObject, false);
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerViewGlobal.setLayoutManager(linearLayoutManager);
            recyclerViewGlobal.setAdapter(globalRankingAdapter);
//            RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
//            recyclerViewGlobal.addItemDecoration(dividerItemDecoration);
        } else {
            globalRankingAdapter.setDataSource(ArrayConvert.toObjectArray(items));
        }
    }

    @Override
    public void setFriendDataSource(ArrayList<Ranking> items) {
        if (friendRankingAdapter == null) {
            friendRankingAdapter = new RankingAdapter(this, ArrayConvert.toObjectArray(items));
            friendRankingAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
                @Override
                public void OnClick(int ItemPosition, Object ItemObject) {
                    presenter.ShowItem(ItemPosition, (Ranking) ItemObject, true);
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerViewFriend.setLayoutManager(linearLayoutManager);
            recyclerViewFriend.setAdapter(friendRankingAdapter);
//            RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(this);
//            recyclerViewFriend.addItemDecoration(dividerItemDecoration);
        } else {
            friendRankingAdapter.setDataSource(ArrayConvert.toObjectArray(items));
        }
    }

    //endregion
}
