package khoavin.sillylearningenglish.FUNCTION.Authentication.Login;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.NetworkDepdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;

/**
 * Created by KhoaVin on 2/21/2017.
 */

public class LoginPresenter implements ILoginPresenter {

    ILoginView loginView;
    @Inject
    IAuthenticationService authenticationService;

    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;

        ((SillyApp) ((AppCompatActivity) loginView).getApplication())
                .getAuthenticationComponent()
                .inject(this);
    }
    @Override
    public void onCreate(Activity activity) {
        authenticationService.FirebaseAuthInit(activity);
    }

    @Override
    public void AttachListener() {
        authenticationService.FirebaseAuthAttach();
    }

    @Override
    public void DetachListener() {
        authenticationService.FirebaseAuthDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        authenticationService.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void OnlineChecking() {
        authenticationService.AddOnlineChecking((Activity)loginView);
    }

    @Override
    public void LogOut() {
        authenticationService.Logout((Activity)loginView);
    }
}
