package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import khoavin.sillylearningenglish.EventListener.FindUserEvent;
import khoavin.sillylearningenglish.EventListener.GetFriendListEvent;
import khoavin.sillylearningenglish.FirebaseObject.UserFriend;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Chat;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class FriendService implements IFriendService {
    final String TAG = "FriendService";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseAccount firebaseAccount;
    List<UserFriend> friendList;
    String temp;
    @Override
    public void getAllFriend(GetFriendListEvent getFriendListEvent) {
        FirebaseDatabase.getInstance().getReference()
                .child("presence").child("74Crfs1XgdYdhIT4FSF9hDbThan2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG,dataSnapshot.getValue().toString());
                UserFriend userFriend = new UserFriend(firebaseAccount,dataSnapshot.getValue(boolean.class));
                friendList.add(userFriend);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        friendList = new ArrayList<UserFriend>();
        databaseReference.child("friends"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    final FirebaseAccount firebaseAccount = postSnapshot.getValue(FirebaseAccount.class);
                    String uid = firebaseAccount.getUid();
                    databaseReference.child("presence/74Crfs1XgdYdhIT4FSF9hDbThan2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e(TAG,dataSnapshot.getValue().toString());
                            UserFriend userFriend = new UserFriend(firebaseAccount,dataSnapshot.getValue(boolean.class));
                            friendList.add(userFriend);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public FirebaseAccount findFriendByName(String name,final FindUserEvent findUserEvent) {
        firebaseAccount = new FirebaseAccount();

        userRef.orderByChild("name").startAt(name).endAt(name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                findUserEvent.findUser(dataSnapshot.getValue(FirebaseAccount.class));
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
