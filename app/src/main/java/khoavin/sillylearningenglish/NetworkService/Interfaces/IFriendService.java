package khoavin.sillylearningenglish.NetworkService.Interfaces;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEventListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public interface IFriendService {
    FirebaseAccount findFriendByName(String name);
    void getAlldFriendUid(FriendEventListener friendEventListener);
    void getListUserImmediately(final FriendEventListener friendEventListener);
    void getListUserRealtime(final ArrayList<String> listFriendsUid,final FriendEventListener friendEventListener);
}
