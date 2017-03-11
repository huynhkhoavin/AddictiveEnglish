package khoavin.sillylearningenglish.NetworkService.Interfaces;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public interface IFriendService {
    FirebaseAccount findFriendByName(String name);
    void getAlldFriendUid(FriendEvent friendEvent);
    void getListUserImmediately(final FriendEvent friendEvent);
    void getListUserRealtime(final ArrayList<String> listFriendsUid,final FriendEvent friendEvent);
}
