package khoavin.sillylearningenglish.FUNCTION.Friend.Presenter;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.FriendEvent;
import khoavin.sillylearningenglish.FUNCTION.Friend.View.IFriendListView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendPresenter implements IFriendPresenter {

    private IFriendListView friendListView;
    private FriendEvent friendEvent;
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
        friendService.getAllFriend();

    }
    @Override
    public void ShowFriendList() {
        friendListView.ShowFriendList(friends);
    }

    public void setFriendEvent(FriendEvent friendEvent) {
        this.friendEvent = friendEvent;
    }

    @Override
    public void searchUser(String username ) {
        friendService.setFriendEvent(friendEvent);
        friendListView.displaySearchedUser(friendService.findFriendByName(username));
    }
}
