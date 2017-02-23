package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Chat;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class FriendService implements IFriendService {
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    public Friend[] getAllFriend() {
        return new Friend[0];
    }

    @Override
    public Friend findFriendByName(String name) {

        return null;
    }

    @Override
    public void addFriend(Friend friend) {

    }

    @Override
    public void sendMessageToUser(Context context, Chat chat, String receiverFirebaseToken) {

    }

    @Override
    public void getMessageFromFirebaseUser(String senderUid, String receiverUid) {

    }

    @Override
    public void sendPushNotificationToReceiver(String username, String message, String uid, String firebaseToken, String receiverFirebaseToken) {

    }
}
