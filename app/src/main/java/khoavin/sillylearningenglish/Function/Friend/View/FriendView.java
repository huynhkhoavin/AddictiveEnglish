package khoavin.sillylearningenglish.Function.Friend.View;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendActionListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.Pattern.ViewPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;
import khoavin.sillylearningenglish.SingleViewObject.Friend;

/**
 * Created by Khoavin on 3/12/2017.
 */

public class FriendView extends ViewPattern{

    final String TAG = "FriendView";
    @BindView(R.id.friend_RecycleView)
    RecyclerView listFriends;
    private FriendListAdapter friendListAdapter;

    public FriendView(Activity activity) {
        super(activity);

        setupAdapter();
    }
    public Activity getActivity(){
        return activity;
    }
    public void ShowFriendFirst(ArrayList<FirebaseAccount> list){
        ArrayList<Friend> friends = new ArrayList<Friend>();
        for(int i = 0; i<list.size();i++){
            friends.add(new Friend(list.get(i).getUid(),list.get(i).getAvatarUrl(),list.get(i).getName(),list.get(i).isOnlineStatus()));
        }
        friendListAdapter.UpdateDataSource(ArrayConvert.toObjectArray(friends));
    }
    public void UpdateListFriends(ArrayList<FirebaseAccount> listFriends) {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        for(int i = 0; i<listFriends.size();i++){
            friends.add(new Friend(listFriends.get(i).getUid(),listFriends.get(i).getAvatarUrl(),listFriends.get(i).getName(),listFriends.get(i).isOnlineStatus()));
        }
        friendListAdapter.UpdateDataSource(ArrayConvert.toObjectArray(friends));
    }
    public void setFriendListener(FriendActionListener friendActionListener){
        friendListAdapter.setFriendActionListener(friendActionListener);
    }
    public void UpdateMessageNotify(String Uid, boolean haveNewMessage)
    {
        int pos = friendListAdapter.SearchPosition(Uid);
        Friend friend = (Friend)(friendListAdapter.getDataSource().get(pos));
        friend.setHave_New_Message(haveNewMessage);
        friendListAdapter.updateDataSource(pos,friend);
    }
    public boolean checkFriendHadSnooze(String Uid){
        return friendListAdapter.checkUidSnoozing(Uid);
    }

    public void setupAdapter(){
        friendListAdapter = new FriendListAdapter(getActivity(),new ArrayList<>());
        listFriends.setAdapter(friendListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        listFriends.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(activity);
        listFriends.addItemDecoration(dividerItemDecoration);
    }
}
