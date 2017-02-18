package khoavin.sillylearningenglish.FUNCTION.HomeMenu.FriendList.Presenter;

import khoavin.sillylearningenglish.FUNCTION.HomeMenu.FriendList.Model.IFriendListModel;
import khoavin.sillylearningenglish.FUNCTION.HomeMenu.FriendList.View.IFriendListView;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendListPresenter implements IFriendListPresenter {

    private IFriendListView friendListView;
    private IFriendListModel friendListModel;

    public FriendListPresenter(IFriendListView flv, IFriendListModel flm){
        this.friendListModel = flm;
        this.friendListView = flv;
    }

    @Override
    public void ShowFriendList() {
        friendListView.ShowFriendList(friendListModel.GetFriendList());
    }

}
