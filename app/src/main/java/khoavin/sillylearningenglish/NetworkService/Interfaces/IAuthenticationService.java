package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.app.Activity;

/**
 * Created by KhoaVin on 2/21/2017.
 */

public interface IAuthenticationService {
    void FirebaseAuthInit(Activity ac);
    void FirebaseLogin(Activity activity,String email, String password);
}
