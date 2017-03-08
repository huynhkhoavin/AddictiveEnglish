package khoavin.sillylearningenglish.FUNCTION.Friend.Presenter;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.FUNCTION.Friend.View.IFriendListView;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendPresenter implements IFriendPresenter {

    private static final String TAG = "FriendPresenter";
    private IFriendListView friendListView;
    @Inject
    IFriendService friendService;
    //@Inject
    //IUserService userService;
    public FriendPresenter(IFriendListView flv){
        this.friendListView = flv;
        ((SillyApp)(((AppCompatActivity)flv).getApplication())).getFriendComponent().inject(this);
    }
    @Override
    public void ServiceTest() {
        searchUser("vin huỳnh");
        UpdateUserStatus();
//        friendService.getAlldFriendUid(new FriendEvent() {
//            @Override
//            public void findUser(FirebaseAccount userAccount) {
//
//            }
//
//            @Override
//            public void getAllFriends(ArrayList<FirebaseAccount> listFriends) {
//                Log.i(TAG,String.valueOf(listFriends.size()));
//                Log.i(TAG,listFriends.get(0).getName());
//            }
//        });
        friendService.getAlldFriendUid(new FriendEvent() {
            @Override
            public void getListFriendsUid(ArrayList<String> listFriendsUid) {
                Log.e(TAG,String.valueOf(listFriendsUid.size()));
                friendService.getListUserDetail(listFriendsUid,this);
            }

            @Override
            public void findUser(FirebaseAccount userAccount) {

            }

            @Override
            public void getAllFriends(ArrayList<FirebaseAccount> listFriends) {
                Log.e(TAG,String.valueOf(listFriends.size()));
            }
        });
    }



    @Override
    public void GetAllFriendsDetail(FriendEvent friendEvent) {
        friendService.getAlldFriendUid(friendEvent);
    }
    @Override
    public void searchUser(String username ) {
        friendService.findFriendByName(username);
    }
    @Override
    public void UpdateUserStatus(){
        ArrayList<FirebaseUser> list = new ArrayList<FirebaseUser>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        list.add(user);
    }
}
