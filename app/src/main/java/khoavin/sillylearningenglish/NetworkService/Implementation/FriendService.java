package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.app.Activity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import khoavin.sillylearningenglish.EventListener.GlobalEvent.GlobalEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEventListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseConstant;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.FriendUid;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_LIST_FRIEND;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.UNFRIEND_REQUEST;

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
    public boolean checkId(String uid, ArrayList<String> uids){
        for (String str:uids){
            if (uid.equals(str)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void getListUserImmediately(final FriendEventListener friendEventListener,final Activity activity){
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
        getAlldFriendUid(fEvent,activity);

    }
    @Override
    public void getAlldFriendUid(final FriendEventListener friendEventListener, final Activity activity){
        //List Friends Of User
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {
                ArrayList<FriendUid> listFriend = ArrayConvert.toArrayList(JsonConvert.getArray(response,FriendUid[].class));

                ArrayList<String> listTemp = new ArrayList<>();
                for (int i = 0; i<listFriend.size();i++){
                    listTemp.add(listFriend.get(i).getUserId());
                }
                friendEventListener.onListFriendsUid(listTemp);
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_LIST_FRIEND;
            }
        };
        networkAsyncTask.execute();
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
    public void unFriend(final Activity activity, final String userUid, final String friendUid, final IVolleyResponse<ErrorCode> volleyResponse) {
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {
                ErrorCode errorCode = ArrayConvert.toArrayList(JsonConvert.getArray(response,ErrorCode[].class)).get(0);
                if (errorCode.getCode() == Common.ServiceCode.UNFRIEND_SUCCESS){
                    Toast.makeText(activity,"Unfriend success",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",userUid);
                params.put("friend_id",friendUid);
                return params;
            }

            @Override
            public String getAPI_URL() {
                return UNFRIEND_REQUEST;
            }
        };
        networkAsyncTask.execute();
    }

    @Override
    public void unFirebaseFriend(final String userUid, final String friendUid, final IVolleyResponse<ErrorCode> volleyResponse) {
        friendRef.child(userUid).child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    friendRef.child(userUid).child(friendUid).removeValue();
                    friendRef.child(friendUid).child(userUid).removeValue();
                    volleyResponse.onSuccess(new ErrorCode("200","Unfriend chat success!"));
                }
                else {
                    // TODO: handle the case where the data does not yet exist
                    volleyResponse.onSuccess(new ErrorCode("219","You're not friend already!"));
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) { }
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
