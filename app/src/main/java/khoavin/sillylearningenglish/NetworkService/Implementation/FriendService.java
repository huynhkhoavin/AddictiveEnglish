package khoavin.sillylearningenglish.NetworkService.Implementation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.GlobalEvent.GlobalEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEventListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseConstant;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class FriendService implements IFriendService {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstant.ARG_USER);
    DatabaseReference friendRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstant.ARG_FRIEND);
    FirebaseAccount userAccount;
    ArrayList<FirebaseAccount> firebaseFriendArrayList = new ArrayList<FirebaseAccount>();
    FriendEventListener friendEventListener;
    GlobalEvent globalEvent;
    int index = 0;
    String temp;
    public final String TAG = "FriendService";
    public FriendService(){

    }
    @Override
    public void getAlldFriendUid(final FriendEventListener friendEventListener){
        //List Friends Of User
        final ArrayList<String> listFriendUid = new ArrayList<>();
        DatabaseReference friendRef = databaseReference.child(FirebaseConstant.ARG_FRIEND).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        friendRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> listUid = new ArrayList<String>();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    listUid.add(data.getValue(String.class));
                }
                friendEventListener.onListFriendsUid(listUid);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public void getListUserImmediately(final FriendEventListener friendEventListener){
        FriendEventListener fEvent = new FriendEventListener() {
            @Override
            public void onListFriendsUid(final ArrayList<String> listFriendsUid) {
                databaseReference.child(FirebaseConstant.ARG_USER).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<FirebaseAccount> firebaseAccounts = new ArrayList<FirebaseAccount>();
                        for (DataSnapshot data:dataSnapshot.getChildren()){
                            FirebaseAccount firebaseAccount = new FirebaseAccount(data.getValue(FirebaseAccount.class));
                            if (checkId(firebaseAccount.getUid(),listFriendsUid)){
                                firebaseAccounts.add(firebaseAccount);
                            }
                        }
                        friendEventListener.onGetAllFriends(firebaseAccounts);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onFindUser(FirebaseAccount userAccount) {

            }

            @Override
            public void onGetAllFriends(ArrayList<FirebaseAccount> listFriends) {

            }
        };
        getAlldFriendUid(fEvent);

    }
    public boolean checkId(String uid, ArrayList<String> uids){
        for (String str:uids){
            if (uid.equals(str)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void getListUserRealtime(final ArrayList<String> listFriendsUid, final FriendEventListener friendEventListener){{
                final ArrayList<FirebaseAccount> listUser = new ArrayList<>();
                databaseReference.child(FirebaseConstant.ARG_USER).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        listUser.clear();
                        for (String uid:listFriendsUid){
                            databaseReference.child(FirebaseConstant.ARG_USER).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    listUser.add(dataSnapshot.getValue(FirebaseAccount.class));
                                    if (listUser.size()==listFriendsUid.size()){
                                        //friendEventListener.onListFriendsUid(listFriendsUid);
                                        friendEventListener.onGetAllFriends(listUser);
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

    }

    @Override
    public void addFriend(String friendUid) {
        if (friendRef.child(userAccount.getUid()).child(friendUid)==null){
            friendRef.child(userAccount.getUid()).child(friendUid).setValue(friendUid);
        }
        friendRef.child(userAccount.getUid()).child(friendUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String friend = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public FirebaseAccount findFriendByName(String name) {
        userAccount = new FirebaseAccount();

        userRef.orderByChild(FirebaseConstant.ARG_USER_NAME).startAt(name).endAt(name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (GlobalEvent.getInstance()!=null)
                for (int i = 0; i<GlobalEvent.getInstance().friendEventListeners.size(); i++){
                    globalEvent.friendEventListeners.get(i).onFindUser(dataSnapshot.getValue(FirebaseAccount.class));
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
}
