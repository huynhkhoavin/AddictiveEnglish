package khoavin.sillylearningenglish.FUNCTION.Friend.Presenter;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.FindUserEvent;
import khoavin.sillylearningenglish.EventListener.GetFriendListEvent;
import khoavin.sillylearningenglish.FUNCTION.Friend.View.IFriendListView;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendPresenter implements IFriendPresenter {
private String TAG = "FriendPresenter";
    private IFriendListView friendListView;
    private FindUserEvent findUserEvent;
    @Inject
    IFriendService friendService;
    private Friend[] friends= new Friend[]{
        new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",false),
                new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",false),
                new Friend(R.drawable.quang_le,"Quang Lê",false),
                new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",true),
                new Friend(R.drawable.quang_le,"Quang Lê",true)
    };
    public FriendPresenter(IFriendListView flv){
        this.friendListView = flv;
        ((SillyApp) ((AppCompatActivity) flv).getApplication())
                .getFriendComponent()
                .inject(this);
            }

    @Override
    public void GetFriendList(GetFriendListEvent getFriendListEvent) {
        friendService.getAllFriend(getFriendListEvent);
    }

    @Override
    public void ShowFriendList() {
        friendListView.ShowFriendList(friends);
    }

    public void setFindUserEvent(FindUserEvent findUserEvent) {
        this.findUserEvent = findUserEvent;
    }

    @Override
    public void searchUser(String username ) {
        friendListView.displaySearchedUser(friendService.findFriendByName(username,new FindUserEvent() {
            @Override
            public void findUser(FirebaseAccount firebaseAccount) {
                Log.e(TAG,firebaseAccount.getName());
            }
        }));
    }
}
