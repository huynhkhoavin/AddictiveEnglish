package khoavin.sillylearningenglish.NetworkDepdency;

import android.app.Application;

import khoavin.sillylearningenglish.EventListener.DI.DaggerEventComponent;
import khoavin.sillylearningenglish.EventListener.DI.EventComponent;
import khoavin.sillylearningenglish.EventListener.DI.EventModule;
import khoavin.sillylearningenglish.NetworkDepdency.Authentication.AuthenticationComponent;
import khoavin.sillylearningenglish.NetworkDepdency.Authentication.AuthenticationModule;
import khoavin.sillylearningenglish.NetworkDepdency.Authentication.DaggerAuthenticationComponent;
import khoavin.sillylearningenglish.NetworkDepdency.Friend.DaggerFriendComponent;
import khoavin.sillylearningenglish.NetworkDepdency.Friend.FriendComponent;
import khoavin.sillylearningenglish.NetworkDepdency.Friend.FriendModule;
import khoavin.sillylearningenglish.NetworkDepdency.Network.DaggerNetworkComponent;
import khoavin.sillylearningenglish.NetworkDepdency.Network.NetworkComponent;
import khoavin.sillylearningenglish.NetworkDepdency.Network.NetworkModule;

public class  SillyApp extends Application {

    private NetworkComponent mNetworkComponent;
    private AuthenticationComponent mAuthenticationComponent;
    private FriendComponent mFriendComponent;
    private EventComponent mEventComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        mNetworkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://192.168.1.108:80/"))
                .build();
        mAuthenticationComponent = DaggerAuthenticationComponent.builder()
                .authenticationModule(new AuthenticationModule())
                .build();
        mFriendComponent = DaggerFriendComponent.builder().friendModule(new FriendModule())
                .build();
        mEventComponent = DaggerEventComponent.builder().eventModule(new EventModule())
                .build();
    }

    public NetworkComponent getNetworkComponent() {

        return this.mNetworkComponent;
    }
    public AuthenticationComponent getAuthenticationComponent(){
        return this.mAuthenticationComponent;
    }
    public FriendComponent getFriendComponent() {
        return this.mFriendComponent;
    }
    public EventComponent getmEventComponent(){
        return this.mEventComponent;
    }
}
