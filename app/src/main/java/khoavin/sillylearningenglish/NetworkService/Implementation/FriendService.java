package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import khoavin.sillylearningenglish.EventListener.FirebaseEventListener;
import khoavin.sillylearningenglish.FirebaseObject.RegisterUser;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Chat;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class FriendService implements IFriendService {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference friendsReference = FirebaseDatabase.getInstance().getReference().child("users");
    RegisterUser registerUser;
    FirebaseEventListener firebaseEventListener;
    String temp;
    @Override
    public Friend[] getAllFriend() {
        return new Friend[0];
    }

    @Override
    public RegisterUser findFriendByName(String name) {
        registerUser = new RegisterUser();

        friendsReference.orderByChild("name").startAt(name).endAt(name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                firebaseEventListener.findUser(dataSnapshot.getValue(RegisterUser.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }

    @Override
    public void setFirebaseEventListener(FirebaseEventListener firebaseEventListener) {
        this.firebaseEventListener = firebaseEventListener;
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
