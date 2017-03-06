package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public void getAlldFriendDetail(final FriendEvent friendEvent){
        //List Friends Of User
        DatabaseReference userRef = databaseReference.child("/users");

        //get All Friend Detail of User Friends
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "User data Change");
                DatabaseReference friendRef = databaseReference.child("/friends/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                friendRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final ArrayList<String> listUid = new ArrayList<String>();
                        for(DataSnapshot data : dataSnapshot.getChildren())
                        {
                            Log.i(TAG,"Can Xem: "+data.getValue().toString());
                            listUid.add(data.getValue().toString());
                        };
                        for (int i = 0; i<listUid.size();i++){
                            GetUserDetail(listUid.get(i), new PersonalEvent() {
                                @Override
                                public void getUserDetail(FirebaseAccount firebaseAccount) {
                                    firebaseFriendArrayList.add(firebaseAccount);
                                    index++;
                                    if(index==listUid.size()){
                                        friendEvent.getAllFriends(firebaseFriendArrayList);
                                    }
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
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public void GetUserDetail(String uid, final PersonalEvent personalEvent){
        databaseReference.child("/users/"+uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i(TAG,dataSnapshot.getValue(FirebaseAccount.class).toString());
                Log.e(TAG,"Get User Detail");
                personalEvent.getUserDetail(dataSnapshot.getValue(FirebaseAccount.class));
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
}
