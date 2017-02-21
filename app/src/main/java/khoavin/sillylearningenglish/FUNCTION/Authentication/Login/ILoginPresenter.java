package khoavin.sillylearningenglish.FUNCTION.Authentication.Login;

import android.app.Activity;

/**
 * Created by KhoaVin on 2/21/2017.
 */

public interface ILoginPresenter {
    void onCreate(final Activity activity);
    void login(String email, String password);
}

