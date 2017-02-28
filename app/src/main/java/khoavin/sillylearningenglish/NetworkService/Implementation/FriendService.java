package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import khoavin.sillylearningenglish.EventListener.FriendEvent;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseUser;
import khoavin.sillylearningenglish.FirebaseObject.UserFriend;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Chat;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class FriendService implements IFriendService {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseUser firebaseUser;
    FriendEvent friendEvent;
    List<UserFriend> friendList;
    String temp;
    @Override
    public void getAllFriend() {
        friendList = new ArrayList<UserFriend>();
        databaseReference.child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public FirebaseUser findFriendByName(String name) {
        firebaseUser = new FirebaseUser();

        userRef.orderByChild("name").startAt(name).endAt(name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                friendEvent.findUser(dataSnapshot.getValue(FirebaseUser.class));
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

    public void setFriendEvent(FriendEvent friendEvent) {
        this.friendEvent = friendEvent;
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
