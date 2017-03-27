package khoavin.sillylearningenglish.Function.Friend.Presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendActionListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEventListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.OffNotifyListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.Function.Friend.ChatObject.ManyChatRoom;
import khoavin.sillylearningenglish.Function.Friend.View.ChatDialog;
import khoavin.sillylearningenglish.Function.Friend.View.FriendView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IChatService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.Pattern.ProgressAsynctask;
import khoavin.sillylearningenglish.SYSTEM.Service.MessageListenerService;
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

    private BroadcastReceiver mReceiver;
    @Inject
    IFriendService friendService;

    @Inject
    IChatService chatService;

    @Inject
    IAuthenticationService authenticationService;

    @Inject
    ManyChatRoom manyChatRoom;
    //@Inject
    //IUserService userService;
    public FriendPresenter(Activity controlActivity){
        this.ControlActivity = controlActivity;
        this.friendView = new FriendView(ControlActivity);
        ((SillyApp)(((AppCompatActivity)ControlActivity).getApplication())).getFriendComponent().inject(this);
        manyChatRoom.SetContext(friendView.getActivity());
        chatDialog = new ChatDialog(controlActivity);
        ((SillyApp)(((AppCompatActivity)ControlActivity).getApplication())).getFriendComponent().inject(chatDialog);
    }


    @Override
    public void DoFunction(){
        ProgressAsynctask progressAsynctask = new ProgressAsynctask() {
            @Override
            public void onDoing() {
                ServiceTest();
                ShowListFriendFirst();
                UpdateListFriend();
                ChatAction();
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };
        progressAsynctask.setContext(friendView.getActivity());
        progressAsynctask.execute();
    }
    @Override
    public void ServiceTest() {
        //UpdateNotify();
        //ListenerNotify();
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
                UpdateNotify();
                ListenerNotify();
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
                if (friendView.checkFriendHadSnooze(friend.getUid()))
                {
                    friendView.UpdateMessageNotify(friend.getUid(),false);
                }
                Log.e(TAG,"Chat: "+String.valueOf(position));
                chatDialog.setTitle(friend.getName());
                    if(chatDialog.getCurrentChatter() == friend) {
                        chatDialog.show();
                        chatDialog.GetMessageFromUid(friend.getUid());
                    }
                    else {

                        chatDialog.Show(friend);
                        chatDialog.GetMessageFromUid(friend.getUid());
                    }

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
    }
    public void UpdateNotify(){
        Intent it  = new Intent(friendView.getActivity(), MessageListenerService.class);
        friendView.getActivity().startService(it);
    }
    public void ListenerNotify(){
        IntentFilter intentFilter = new IntentFilter(
                "MESSAGE_NOTIFY");

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                final String Talker_Uid = intent.getStringExtra("UID");
                //Get new Message from new Friend
                if (Talker_Uid!=null){
                    //Update Notify trong giao dien

                    if (chatDialog.isShowing())
                    {
                        if (chatDialog.getCurrentChatter().getUid().equals(Talker_Uid))
                        {
                            return;
                        }
                    }
                    friendView.UpdateMessageNotify(Talker_Uid,true);
                }
            }
        };
        //registering our receiver
        this.friendView.getActivity().registerReceiver(mReceiver, intentFilter);
    }
}