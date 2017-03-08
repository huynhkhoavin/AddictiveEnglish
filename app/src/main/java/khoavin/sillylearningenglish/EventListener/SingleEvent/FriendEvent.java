package khoavin.sillylearningenglish.EventListener.SingleEvent;

import java.util.ArrayList;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/26/2017.
 */

public interface FriendEvent {
    void getListFriendsUid(ArrayList<String> listFriendsUid);
    void findUser(FirebaseAccount userAccount);
    void getAllFriends(ArrayList<FirebaseAccount> listFriends);
}
