package khoavin.sillylearningenglish.FUNCTION.Friend.Presenter;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IFriendPresenter {
    void ServiceTest();
    void GetAllFriendsDetail(FriendEvent friendEvent);
    void searchUser(String username);
    void UpdateUserStatus();

}
