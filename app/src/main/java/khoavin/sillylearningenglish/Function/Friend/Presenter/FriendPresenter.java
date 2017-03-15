package khoavin.sillylearningenglish.Function.Friend.Presenter;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.SingleEvent.ChatListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendActionListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEventListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.SendMessageListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.Function.Friend.View.ChatDialog;
import khoavin.sillylearningenglish.Function.Friend.View.ChatDialogFragment;
import khoavin.sillylearningenglish.Function.Friend.View.FriendView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Implementation.ChatService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IChatService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.SingleViewObject.ChatItem;
import khoavin.sillylearningenglish.SingleViewObject.Friend;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendPresenter implements IFriendPresenter {

    private static final String TAG = "FriendPresenter";
    private Activity ControlActivity;
    private FriendView friendView;
    private FriendActionListener friendActionListener;
    private ChatDialog chatDialog;

    @Inject
    IFriendService friendService;

    @Inject
    IChatService chatService;

    @Inject
    IAuthenticationService authenticationService;
    //@Inject
    //IUserService userService;
    public FriendPresenter(Activity controlActivity){
        this.ControlActivity = controlActivity;
        this.friendView = new FriendView(ControlActivity);
        ((SillyApp)(((AppCompatActivity)ControlActivity).getApplication())).getFriendComponent().inject(this);
        chatDialog = new ChatDialog(controlActivity);
    }

    @Override
    public void DoFunction(){
        ShowListFriendFirst();
        UpdateListFriend();
        ServiceTest();
    }
    @Override
    public void ServiceTest() {
        ChatAction();
    }
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void searchUser(String username ) {
        friendService.findFriendByName(username);
    }

    public void ShowListFriendFirst(){
        friendService.getListUserImmediately(new FriendEventListener() {
            @Override
            public void onListFriendsUid(ArrayList<String> listFriendsUid) {

            }

            @Override
            public void onFindUser(FirebaseAccount userAccount) {

            }

            @Override
            public void onGetAllFriends(ArrayList<FirebaseAccount> listFriends) {
                friendView.ShowFriendFirst(listFriends);
            }
        });
    }
    public void UpdateListFriend(){
        friendService.getAlldFriendUid(new FriendEventListener() {
            @Override
            public void onListFriendsUid(ArrayList<String> listFriendsUid) {
                friendService.getListUserRealtime(listFriendsUid,this);
            }

            @Override
            public void onFindUser(FirebaseAccount userAccount) {

            }

            @Override
            public void onGetAllFriends(ArrayList<FirebaseAccount> listFriends) {
                friendView.UpdateListFriends(listFriends);
            }
        });
    }
    public void ChatAction(){
        friendActionListener = new FriendActionListener() {
            @Override
            public void ChatAction(int position, final Friend friend) {
                Log.e(TAG,"Chat: "+String.valueOf(position));
                chatDialog.setTitle(friend.getName());
                chatDialog.show();
                chatDialog.SetChatListener(new ChatListener() {
                    @Override
                    public void SendMessage(String Message) {
                        chatDialog.AddChatItem(new ChatItem(friend.getAvatar(),Message));
                        chatService.sendMessageToUid(getCurrentUser().getUid(), friend.getUid(), Message, new SendMessageListener() {
                            @Override
                            public void OnSendSuccess() {
                                Log.e(TAG, "success");
                            }

                            @Override
                            public void OnSendFailed() {
                                Log.e(TAG,"fail");
                            }
                        });
                    }
                });
            }

            @Override
            public void ChallengeAction(int position,Friend friend) {
                Log.e(TAG,"Challenge: "+String.valueOf(position));
            }

            @Override
            public void GetInfoAction(int position,Friend friend) {
                Log.e(TAG,"Get Info: "+String.valueOf(position));
            }

            @Override
            public void UnFriend(int position,Friend friend) {
                Log.e(TAG,"Unfriend: "+String.valueOf(position));
            }
        };
        friendView.setFriendListener(friendActionListener);
        chatService.getNewMessage();
    }
}
