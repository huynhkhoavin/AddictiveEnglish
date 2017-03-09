package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.GlobalEvent.GlobalEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.PersonalEvent;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class FriendService implements IFriendService {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseAccount userAccount;
    ArrayList<FirebaseAccount> firebaseFriendArrayList = new ArrayList<FirebaseAccount>();
    FriendEvent friendEvent;
    GlobalEvent globalEvent;
    int index = 0;
    String temp;
    public final String TAG = "FriendService";
    public FriendService(){
    }
    @Override
    public void getAlldFriendUid(final FriendEvent friendEvent){
        //List Friends Of User
        final ArrayList<String> listFriendUid = new ArrayList<>();
        DatabaseReference userRef = databaseReference.child("/users");
        DatabaseReference friendRef = databaseReference.child("/friends/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        //get All Friend Detail of User Friends
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "User data Change");
                final DatabaseReference friendRef = databaseReference.child("/friends/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                friendRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listFriendUid.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            listFriendUid.add(data.getValue(String.class));
                        }
                        getListUserDetail(listFriendUid,friendEvent);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        friendRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> listUid = new ArrayList<String>();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    listUid.add(data.getValue(String.class));
                }
                friendEvent.getListFriendsUid(listUid);
                getListUserImmediately(listUid,friendEvent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void getListUserImmediately(final ArrayList<String> listUserUid, final FriendEvent friendEvent){
        DatabaseReference userRef = databaseReference.child("users");
        final ArrayList<FirebaseAccount> listUser = new ArrayList<>();
        databaseReference.child("users/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FirebaseAccount> firebaseAccounts = new ArrayList<FirebaseAccount>();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    FirebaseAccount firebaseAccount = new FirebaseAccount(data.getValue(FirebaseAccount.class));
                    for (String string:listUserUid){
                        if (checkId(firebaseAccount.getUid(),listUserUid)){
                            firebaseAccounts.add(firebaseAccount);
                        }
                    }
                }
                friendEvent.getAllFriends(firebaseAccounts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public boolean checkId(String uid, ArrayList<String> uids){
        for (String str:uids){
            if (uid.equals(str)){
                return true;
            }
        }
        return false;
    }
    public void getListUserDetail(final ArrayList<String> listUserUid, final FriendEvent friendEvent){
        DatabaseReference userRef = databaseReference.child("users");
        final ArrayList<FirebaseAccount> listUser = new ArrayList<>();
            databaseReference.child("/users").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    for (String uid:listUserUid){
                       databaseReference.child("/users/"+uid).addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               listUser.add(dataSnapshot.getValue(FirebaseAccount.class));
                               if (listUser.size()==listUserUid.size()){
                                   friendEvent.getListFriendsUid(listUserUid);
                                   friendEvent.getAllFriends(listUser);
                               }
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                    }
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
    }
    @Override
    public FirebaseAccount findFriendByName(String name) {
        userAccount = new FirebaseAccount();

        userRef.orderByChild("name").startAt(name).endAt(name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (GlobalEvent.getInstance()!=null)
                for (int i = 0; i<GlobalEvent.getInstance().friendEvents.size();i++){
                    globalEvent.friendEvents.get(i).findUser(dataSnapshot.getValue(FirebaseAccount.class));
                }
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

    public void getAllFriendsFirst(final FriendEvent friendEvent){
        DatabaseReference friendRef = databaseReference.child("/friends/"+FirebaseAuth.getInstance().getCurrentUser());
        friendRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> listUid = new ArrayList<String>();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    listUid.add(data.getValue(String.class));
                }
                friendEvent.getListFriendsUid(listUid);
                getListUserDetail(listUid,friendEvent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
