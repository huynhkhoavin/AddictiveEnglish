package khoavin.sillylearningenglish.Function.Friend.Presenter;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import javax.inject.Inject;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.SendMessageListener;
import khoavin.sillylearningenglish.Function.Friend.View.IFriendListView;
import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IChatService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFriendService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IUserService;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class FriendPresenter implements IFriendPresenter {

    private static final String TAG = "FriendPresenter";
    private IFriendListView friendListView;
    @Inject
    IFriendService friendService;

    @Inject
    IChatService chatService;

    @Inject
    IAuthenticationService authenticationService;
    //@Inject
    //IUserService userService;
    public FriendPresenter(IFriendListView flv){
        this.friendListView = flv;
        ((SillyApp)(((AppCompatActivity)flv).getApplication())).getFriendComponent().inject(this);
    }
    @Override
    public void ServiceTest() {

        //Chat
        chatService.sendMessageToUid(getCurrentUser().getUid(), "GAMJNCtdsAVT2O7CRCFxN38QLnX2", "hello new chat", new SendMessageListener() {
            @Override
            public void OnSendSuccess() {
                Log.e(TAG, "Send Message Success!");
            }

            @Override
            public void OnSendFailed() {

            }
        });
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
    public FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void searchUser(String username ) {
        friendService.findFriendByName(username);
    }
}
