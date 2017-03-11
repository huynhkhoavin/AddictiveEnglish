package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.app.Activity;
import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by KhoaVin on 2/21/2017.
 */

public interface IAuthenticationService {
    void FirebaseAuthInit(Activity ac);
    void FirebaseAuthAttach();
    void FirebaseAuthDetach();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void LoginSuccess(Activity activity);
    void LoginFail(Activity activity);
    void Logout(Activity activity);
    void AddOnlineChecking(Activity activity);
    FirebaseUser getCurrentUser();
}
