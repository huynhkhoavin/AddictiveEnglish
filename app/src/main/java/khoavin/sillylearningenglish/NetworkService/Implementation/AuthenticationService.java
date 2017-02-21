package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import khoavin.sillylearningenglish.FUNCTION.Authentication.Login.OnLoginListener;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;

/**
 * Created by KhoaVin on 2/21/2017.
 */

public class AuthenticationService implements IAuthenticationService {
    private static final int RC_SIGN_IN = 1;
    private OnLoginListener mOnLoginListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void FirebaseAuthInit(final Activity activity) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
                    activity.startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void FirebaseLogin(final Activity activity, String email, String password) {


    }
}
