package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.app.Activity;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEventListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public interface IFriendService {
    FirebaseAccount findFriendByName(String name);
    void getListUserRealtime(final ArrayList<String> listFriendsUid,final FriendEventListener friendEventListener);
    void addFriend(String userUid, String friendUid, final IVolleyResponse<ErrorCode> volleyResponse);
    void unFriend(Activity activity, String userUid, String friendUid, final IVolleyResponse<ErrorCode> volleyResponse);
    void unFirebaseFriend(String userUid, String friendUid, final IVolleyResponse<ErrorCode> volleyResponse);
    public void getAlldFriendUid(final FriendEventListener friendEventListener, Activity activity);
    public void getListUserImmediately(final FriendEventListener friendEventListener, ArrayList<String> listFriendUid);
}
