package khoavin.sillylearningenglish.FUNCTION.Friend.Presenter;

import khoavin.sillylearningenglish.EventListener.FindUserEvent;
import khoavin.sillylearningenglish.EventListener.GetFriendListEvent;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IFriendPresenter {
    void GetFriendList(GetFriendListEvent getFriendListEvent);
    void ShowFriendList();
    void setFindUserEvent(FindUserEvent findUserEvent);
    void searchUser(String username);
}
