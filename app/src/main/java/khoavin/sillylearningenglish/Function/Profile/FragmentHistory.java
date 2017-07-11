package khoavin.sillylearningenglish.Function.Profile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.Function.Arena.Presenters.Implementation.BattleHistoryPresenter;
import khoavin.sillylearningenglish.Function.Arena.Views.BattleHistoryAdapter;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeActivity;
import khoavin.sillylearningenglish.Function.UIView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleHistory;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleHistoryInfo;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.BlurBuilder;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by KhoaVin on 10/07/2017.
 */

public class FragmentHistory extends FragmentPattern {
    @BindView(R.id.imgUserAvatar)
    ImageView imgUserAvatar;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.rank_icon)
    ImageView rankIcon;
    @BindView(R.id.rank_name)
    TextView rankName;
    @BindView(R.id.tvCoinNumber)
    TextView coinNumber;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.blur_background)
    ImageView blurBackground;

    @Inject
    IAuthenticationService authenticationService;
    @Inject
    IArenaService arenaService;
    @Inject
    IVolleyService volleyService;
    @Inject
    IPlayerService playerService;

    /**
     * The histories data.
     */
    private ArrayList<BattleHistory> _histories;

    BattleHistoryAdapter battleHistoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_info,container,false);
        ButterKnife.bind(this,v);
        ((SillyApp) getActivity().getApplication()).getDependencyComponent().inject(this);
        getUserInfo();
        SetUpAdapter();
        getBattleHistory();
        return v;
    }
    public void getUserInfo(){
        userName.setText(authenticationService.getCurrentUser().getDisplayName());
        Glide.with(getContext()).load(authenticationService.getCurrentUser().getPhotoUrl()).placeholder(R.drawable.avatar_holder).into(imgUserAvatar);
        coinNumber.setText(playerService.GetCurrentUser().getCoin().toString());
        rankIcon.setImageResource(Common.getRankMedalImage(playerService.GetCurrentUser().getLevel()));
        rankName.setText(Common.GetMedalTitleFromLevel(playerService.GetCurrentUser().getLevel(),getContext()));
        Glide.with(getContext())
                .load(authenticationService.getCurrentUser().getPhotoUrl())    // you can pass url too
//                .load(R.drawable.avatar)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        // you can do something with loaded bitmap here
                        Bitmap blurredBitmap = BlurBuilder.blur( getContext(), resource );

                        //blurBackground.setBackgroundDrawable( new BitmapDrawable( getResources(), blurredBitmap ) );
                        //blurBackground.setImageBitmap(blurredBitmap);
                        Blurry.with(getContext()).radius(30).from(resource).into(blurBackground);

                    }
                });
    }
    public void getBattleHistory(){
        arenaService.GetBattleHistory(authenticationService.getCurrentUser().getUid(), getContext(), volleyService, new IVolleyResponse<BattleHistoryInfo[]>() {
            @Override
            public void onSuccess(BattleHistoryInfo[] responseObj) {
                HandleHistory(responseObj);
                battleHistoryAdapter.setDataSource(ArrayConvert.toObjectArray(_histories));
            }

            @Override
            public void onError(ErrorCode errorCode) {

            }
        });
    }
    public void SetUpAdapter(){
        battleHistoryAdapter = new BattleHistoryAdapter(getContext(), new ArrayList<>());
        battleHistoryAdapter.setAdapterOnItemClick(new AdapterOnItemClick() {
            @Override
            public void OnClick(int ItemPosition, Object ItemObject) {
                //Do something when selected item.
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(battleHistoryAdapter);
    }
    public void HandleHistory(BattleHistoryInfo[] info){
        if (_histories == null && info != null && info.length > 0) {
            _histories = new ArrayList<BattleHistory>();
            long timeDiff = 0;
            for (int i = 0; i < info.length; i++) {
                BattleHistoryInfo historyInfo = info[i];
                BattleHistory battleHistory = new BattleHistory();
                if (historyInfo.getAttackerId().equals(playerService.GetCurrentUser().getUserId())) {
                    //current user is attacker.
                    battleHistory.setUserAvatar(historyInfo.getAttackerAvatar());
                    battleHistory.setUserName(historyInfo.getAttackerName());
                    battleHistory.setUserTrueAnswer(historyInfo.getTotalAttackerAnswer());

                    timeDiff = historyInfo.getAttackerEndTime().getTime() - historyInfo.getAttackerBeginTime().getTime();
                    battleHistory.setUserTotalTime(timeDiff);

                    //enemy is defender.
                    battleHistory.setEnemyAvatar(historyInfo.getDefenderAvatar());
                    battleHistory.setEnemyName(historyInfo.getDefenderName());
                    battleHistory.setEnemyTrueAnswer(historyInfo.getTotalDefenderAnswer());

                    timeDiff = historyInfo.getDefenderEndTime().getTime() - historyInfo.getDefenderBeginTime().getTime();
                    battleHistory.setEnemyTotalTime(timeDiff);

                    if (historyInfo.getAttackerId().equals(historyInfo.getVictoryId())) {
                        battleHistory.setVictoryBattle(true);
                    } else {
                        battleHistory.setVictoryBattle(false);
                    }
                    battleHistory.setDateCreate(historyInfo.getDateCreate());
                } else {
                    //enemy is attacker
                    battleHistory.setEnemyAvatar(historyInfo.getAttackerAvatar());
                    battleHistory.setEnemyName(historyInfo.getAttackerName());
                    battleHistory.setEnemyTrueAnswer(historyInfo.getTotalAttackerAnswer());

                    timeDiff = historyInfo.getAttackerEndTime().getTime() - historyInfo.getAttackerBeginTime().getTime();
                    battleHistory.setEnemyTotalTime(timeDiff);


                    //current user is defender.
                    battleHistory.setUserAvatar(historyInfo.getDefenderAvatar());
                    battleHistory.setUserName(historyInfo.getDefenderName());
                    battleHistory.setUserTrueAnswer(historyInfo.getTotalDefenderAnswer());

                    timeDiff = historyInfo.getDefenderEndTime().getTime() - historyInfo.getDefenderBeginTime().getTime();
                    battleHistory.setUserTotalTime(timeDiff);

                    if (historyInfo.getDefenderId().equals(historyInfo.getVictoryId())) {
                        battleHistory.setVictoryBattle(true);
                    } else {
                        battleHistory.setVictoryBattle(false);
                    }

                    battleHistory.setDateCreate(historyInfo.getDateCreate());
                }

                _histories.add(battleHistory);
            }
        }
    }
}
