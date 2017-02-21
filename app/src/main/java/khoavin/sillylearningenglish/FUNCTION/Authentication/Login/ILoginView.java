package khoavin.sillylearningenglish.FUNCTION.Authentication.Login;

/**
 * Created by KhoaVin on 2/21/2017.
 */

public interface ILoginView {
    void onLoginSuccess(String message);

    void onLoginFailure(String message);
}
