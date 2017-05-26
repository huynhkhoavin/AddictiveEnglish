package khoavin.sillylearningenglish.Function.Ranking.Presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.BattlePrepareActivity;
import khoavin.sillylearningenglish.Function.Ranking.Views.IRankingView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IRankingService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Ranking;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 5/24/2017.
 */

public class RankingPresenter implements IRankingPresenter {

    /**
     * The ranking view.
     */
    private IRankingView _rankingView;

    /**
     * Storage friend ranking.
     */
    private ArrayList<Ranking> _friendRankings;

    /**
     * Storage global ranking.
     */
    private ArrayList<Ranking> _globalRanking;

    /**
     * Inject the ranking service.
     */
    @Inject
    IRankingService rankingService;

    /**
     * Gets the player service.
     */
    @Inject
    IPlayerService playerService;

    /**
     * Gets the volley service.
     */
    @Inject
    IVolleyService volleyService;

    /**
     * Inject arena service.
     */
    @Inject
    IArenaService arenaService;

    /**
     * Gets the value storage current preview ranking item.
     */
    private Ranking _currentRanking;

//    /**
//     * Gets the value indicate current ranking preview state.
//     * true if current preview friend ranking, otherwise false.
//     */
//    private boolean _isFriendRanking = false;
//
//    /**
//     * Gets the value storage current preview ranking item.
//     */
//    private int _currentRankingPosition = 0;

    /**
     * Gets the ranking view as AppCompatActivity.
     *
     * @return
     */
    private AppCompatActivity GetView() {
        return (AppCompatActivity) _rankingView;
    }

    /**
     * Initialize the ranking presenter.
     *
     * @param rankingView The ranking view.
     */
    public RankingPresenter(IRankingView rankingView) {
        this._rankingView = rankingView;
        //Inject dependency
        ((SillyApp) GetView().getApplication())
                .getDependencyComponent()
                .inject(this);

        //Default get global ranking.
        GetGlobalRanking();

    }

    //region Implementation

    @Override
    public void AddFriend() {

        if (_currentRanking != null &&
                !_currentRanking.getIsUserFriend() &&
                _currentRanking.getUserId() != playerService.GetCurrentUser().getUserId()) {
            rankingService.AddFriend(playerService.GetCurrentUser().getUserId(), _currentRanking.getUserId(), GetView(), volleyService, new IVolleyResponse<ErrorCode>() {
                @Override
                public void onSuccess(ErrorCode responseCode) {

                    switch (responseCode.getCode())
                    {
                        case COMPLETED:
                            Common.ShowInformMessage("Gửi lời mời kết bạn thành công.", "Thông báo", "Ok", GetView(), null);
                            break;
                        case ALREADY_FRIEND:
                            Common.ShowInformMessage("Đã kết bạn trước đó.", "Thông báo", "Ok", GetView(), null);
                            break;
                        case FRIEND_NOT_FOUND:
                            Common.ShowInformMessage("Không tìm thấy thông tin người dùng của đối tượng muốn kết bạn.", "Thông báo", "Ok", GetView(), null);
                            break;
                        case USER_NOT_FOUND:
                            Common.ShowInformMessage("Không tìm thấy thông tin người dùng của bạn.", "Thông báo", "Ok", GetView(), null);
                            break;
                        default:
                            Common.ShowInformMessage("Không rõ phản hồi, mã phản hồi: " + responseCode.getCode().getValue(), "Thông báo", "Ok", GetView(), null);
                            break;
                    }
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    Common.ShowInformMessage("Không thể gửi lời mời kết bạn", "Thông báo", "Ok", GetView(), null);
                }
            });
        }

    }

    @Override
    public void Challenge() {
        arenaService.GetEnemyInformationFromRankingItem(_currentRanking);
        arenaService.SetBattleCalledFrom(Common.BattleCalledFrom.FROM_RANKING);
        Intent it = new Intent(GetView(), BattlePrepareActivity.class);
        GetView().startActivity(it);
    }

    @Override
    public void ShowItem(int itemPosition, Ranking item, boolean isFriendRanking) {
//        this._isFriendRanking = isFriendRanking;
//        this._currentRankingPosition = itemPosition;
        this._currentRanking = item;
        this.ToTheView(item);
    }

    @Override
    public void ShowFirstFriendRankingItem() {
        if(_friendRankings != null && _friendRankings.size() > 0)
            ToTheView(_friendRankings.get(0));
    }

    @Override
    public void ShowFirstGlobalRankingItem() {
        if(_globalRanking != null && _globalRanking.size() > 0)
            ToTheView(_globalRanking.get(0));
    }

    @Override
    public void GetGlobalRanking() {
        if (_globalRanking == null) {
            rankingService.GetGlobalRanking(playerService.GetCurrentUser().getUserId(), GetView(), volleyService, new IVolleyResponse<ArrayList<Ranking>>() {
                @Override
                public void onSuccess(ArrayList<Ranking> globalItems) {
                    _globalRanking = globalItems;
                    _rankingView.setGlobalDataSource(_globalRanking);
                    ShowFirstGlobalRankingItem();
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    Common.ShowInformMessage(errorCode.getDetails(), "Thông báo", "Đồng ý", GetView(), null);
                }
            });
        }
        else
        {
            ShowFirstGlobalRankingItem();
        }
    }

    @Override
    public void GetFriendRanking() {
        if (_friendRankings == null) {
            rankingService.GetFriendRanking(playerService.GetCurrentUser().getUserId(), GetView(), volleyService, new IVolleyResponse<ArrayList<Ranking>>() {
                @Override
                public void onSuccess(ArrayList<Ranking> friendItems) {
                    _friendRankings = friendItems;
                    _rankingView.setFriendDataSource(_friendRankings);
                    ShowFirstFriendRankingItem();
                }

                @Override
                public void onError(ErrorCode errorCode) {
                    Common.ShowInformMessage(errorCode.getDetails(), "Thông báo", "Đồng ý", GetView(), null);
                }
            });
        }
        else
        {
            ShowFirstFriendRankingItem();
        }
    }

    //endregion

    /**
     * Set value to the view.
     * @param item
     */
    private void ToTheView(Ranking item)
    {
        _rankingView.setAddFriendButtonState(item.getIsUserFriend(), item.getUserId().equals(playerService.GetCurrentUser().getUserId()));
        _rankingView.setUserName(item.getUserName());
        _rankingView.setUserAvatar(item.getUserAvatar());
        _rankingView.setUserMedal(Common.getRankMedalImage(item.getLevel()));
        _rankingView.setUserRankPosition(Common.getUserRankPositionText(item.getRankPosition()));
        _rankingView.setUserWinRate(Common.GetWinRate(item.getTotalBattle(), item.getWinBattle()));
        _rankingView.setUserRankTitle(Common.GetMedalTitleFromLevel(item.getLevel(), GetView()));
        _rankingView.setTotalBattle(Common.FormatBigNumber(item.getTotalBattle()));
    }
}
