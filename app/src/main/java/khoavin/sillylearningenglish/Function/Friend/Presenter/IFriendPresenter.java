package khoavin.sillylearningenglish.Function.Friend.Presenter;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IFriendPresenter {
    void ServiceTest();
    void GetAllFriendUid(FriendEvent friendEvent);
    void GetAllFriendsRealtime(ArrayList<String> listUid,FriendEvent friendEvent);
    void GetAllFriendsImmediatly(FriendEvent friendEvent);
    void searchUser(String username);
    void UpdateUserStatus();

}
