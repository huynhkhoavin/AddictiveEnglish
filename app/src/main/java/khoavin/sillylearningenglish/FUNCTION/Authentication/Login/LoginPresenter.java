package khoavin.sillylearningenglish.FUNCTION.Authentication.Login;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import khoavin.sillylearningenglish.NetworkDepdency.Network.SillyApp;
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

    }

    @Override
    public void login(String email, String password) {

    }
}
