package khoavin.sillylearningenglish.FUNCTION.Authentication.Login;

/**
 * Created by KhoaVin on 2/21/2017.
 */

public interface OnLoginListener {
    void onSuccess(String message);

    void onFailure(String message);
}
