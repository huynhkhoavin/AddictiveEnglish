package khoavin.sillylearningenglish.EventListener.SingleEvent;

import java.util.ArrayList;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/26/2017.
 */

public interface FriendEventListener {
    void onListFriendsUid(ArrayList<String> listFriendsUid);
    void onFindUser(FirebaseAccount userAccount);
    void onGetAllFriends(ArrayList<FirebaseAccount> listFriends);
}
