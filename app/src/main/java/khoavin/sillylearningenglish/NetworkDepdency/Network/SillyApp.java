package khoavin.sillylearningenglish.NetworkDepdency.Network;

import android.app.Application;

import khoavin.sillylearningenglish.NetworkDepdency.Authentication.AuthenticationComponent;
import khoavin.sillylearningenglish.NetworkDepdency.Authentication.AuthenticationModule;
import khoavin.sillylearningenglish.NetworkDepdency.Authentication.DaggerAuthenticationComponent;

public class SillyApp extends Application {

    private NetworkComponent mComponent;
    private AuthenticationComponent mAuthenticationComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://192.168.1.108:80/"))
                .build();
        mAuthenticationComponent = DaggerAuthenticationComponent.builder()
                .authenticationModule(new AuthenticationModule())
                .build();
    }

    public NetworkComponent getNetComponent() {
        return this.mComponent;
    }
    public AuthenticationComponent getAuthenticationComponent(){
        return this.mAuthenticationComponent;
    }
}
