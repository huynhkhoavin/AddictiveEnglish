package khoavin.sillylearningenglish.Function.Friend.Presenter;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.Function.Friend.View.IFriendListView;
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
    }

    @Override
    public void GetAllFriendUid(FriendEvent friendEvent) {
        friendService.getAlldFriendUid(friendEvent);
    }

    @Override
    public void GetAllFriendsRealtime(ArrayList<String> listUid,FriendEvent friendEvent) {
        friendService.getListUserRealtime(listUid,friendEvent);
    }

    @Override
    public void GetAllFriendsImmediatly(FriendEvent friendEvent) {
        friendService.getListUserImmediately(friendEvent);
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
