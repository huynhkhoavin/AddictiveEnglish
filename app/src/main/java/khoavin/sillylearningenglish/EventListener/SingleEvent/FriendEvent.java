package khoavin.sillylearningenglish.EventListener.SingleEvent;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/26/2017.
 */

public interface FriendEvent {
    void findUser(FirebaseAccount userAccount);
    void getAllFriends(FirebaseAccount[] userAccounts);
}