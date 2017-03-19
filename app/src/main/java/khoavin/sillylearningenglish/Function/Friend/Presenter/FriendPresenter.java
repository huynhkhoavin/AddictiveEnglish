package khoavin.sillylearningenglish.Function.Friend.Presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.SingleEvent.ChatListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendActionListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEventListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.GetMessageListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.SendMessageListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseConstant;
import khoavin.sillylearningenglish.Function.Friend.View.ChatDialog;
import khoavin.sillylearningenglish.Function.Friend.View.FriendView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Implementation.ChatService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IChatService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.Pattern.ProgressAsynctask;
import khoavin.sillylearningenglish.SYSTEM.Service.MessageListenerService;
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
    private BroadcastReceiver mReceiver;
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
        ProgressAsynctask progressAsynctask = new ProgressAsynctask() {
            @Override
            public void onDoing() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ServiceTest();
                ShowListFriendFirst();
                UpdateListFriend();
                ChatAction();
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
                Log.e(TAG,"Chat: "+String.valueOf(position));
                chatDialog.setTitle(friend.getName());
                chatDialog.show();

                //Click Send Button will do
                chatDialog.SetChatListener(new ChatListener() {
                    @Override
                    public void SendMessage(String Message) {
                        chatDialog.AddChatItem(new ChatItem(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(),Message));
                        chatService.sendMessageToUid(getCurrentUser().getUid(), friend.getUid(), Message, new SendMessageListener() {
                            @Override
                            public void OnSendSuccess() {
                                Log.e(TAG, "success");
                                friendView.UpdateMessageNotify(friend.getUid(),false);
                            }

                            @Override
                            public void OnSendFailed() {

                                Log.e(TAG,"fail");
                            }
                        });
                    }
                });
                //do without any condition
                chatService.getMessageFromUid(friend.getUid(), new GetMessageListener() {
                    @Override
                    public void OnSuccess(ArrayList<String> listMessage) {
                        for (String message : listMessage)
                        {
                            chatDialog.AddChatItem(new ChatItem(friend.getAvatar().toString(),message));
                        }
                        friendView.UpdateMessageNotify(friend.getUid(),false);
                        final DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstant.ARG_CHAT_ROOMS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(friend.getUid());
                        ChatRef.removeValue();
                    }
                    @Override
                    public void OnError(String Error) {

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
                final String new_friend_talk_Uid = intent.getStringExtra("UID");
                //Get new Message from new Friend
                if (new_friend_talk_Uid!=null){
                    //Update Notify trong giao dien
                    friendView.UpdateMessageNotify(new_friend_talk_Uid,true);
                }
                String newMessage = intent.getStringExtra("CHANGE");
                if (newMessage!=null)
                {
                    //Update Notify trong giao dien
                }
            }
        };
        //registering our receiver
        this.friendView.getActivity().registerReceiver(mReceiver, intentFilter);
    }
}
