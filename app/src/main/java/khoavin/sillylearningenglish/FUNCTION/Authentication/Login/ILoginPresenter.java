package khoavin.sillylearningenglish.FUNCTION.Authentication.Login;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by KhoaVin on 2/21/2017.
 */

public interface ILoginPresenter {
    void onCreate(final Activity activity);
    void AttachListener();
    void DetachListener();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void OnlineChecking();
    void LogOut();
}

