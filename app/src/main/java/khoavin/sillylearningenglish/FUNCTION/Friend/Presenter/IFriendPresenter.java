package khoavin.sillylearningenglish.FUNCTION.Friend.Presenter;

import khoavin.sillylearningenglish.EventListener.FriendEvent;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IFriendPresenter {
    void ShowFriendList();
    void setFriendEvent(FriendEvent friendEvent);
    void searchUser(String username);
}
