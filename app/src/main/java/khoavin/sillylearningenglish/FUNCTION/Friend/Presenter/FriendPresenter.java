package khoavin.sillylearningenglish.FUNCTION.Friend.Presenter;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.GlobalEvent.GlobalEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.PersonalEvent;
import khoavin.sillylearningenglish.FUNCTION.Friend.View.IFriendListView;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IUserService;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendPresenter implements IFriendPresenter {

    private static final String TAG = "FriendPresenter";
    private IFriendListView friendListView;
    @Inject
    IFriendService friendService;
    @Inject
    IUserService userService;
    public FriendPresenter(IFriendListView flv){
        this.friendListView = flv;
        ((SillyApp)(((AppCompatActivity)flv).getApplication())).getFriendComponent().inject(this);
    }
    @Override
    public void ServiceTest() {
        searchUser("vin huỳnh");
        UpdateUserStatus();
//        friendService.getAlldFriendDetail(new FriendEvent() {
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

    }



    @Override
    public void GetAllFriendsDetail(FriendEvent friendEvent) {
        friendService.getAlldFriendDetail(friendEvent);
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
