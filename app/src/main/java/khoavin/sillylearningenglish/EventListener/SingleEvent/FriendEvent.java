package khoavin.sillylearningenglish.EventListener.SingleEvent;

import khoavin.sillylearningenglish.FirebaseObject.UserAccount;

/**
 * Created by KhoaVin on 2/26/2017.
 */

public interface FriendEvent {
    void findUser(UserAccount userAccount);
    void getAllFriends(UserAccount[] userAccounts);
}
