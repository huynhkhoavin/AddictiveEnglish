package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import khoavin.sillylearningenglish.FirebaseObject.UserAccount;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Chat;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public interface IFriendService {
    void getAllFriend();
    UserAccount findFriendByName(String name);
    void addFriend(Friend friend);
    void sendMessageToUser(final Context context, final Chat chat, final String receiverFirebaseToken);
    void getMessageFromFirebaseUser(String senderUid, String receiverUid);
    void sendPushNotificationToReceiver(String username,String message,String uid,String firebaseToken,String receiverFirebaseToken);
}
