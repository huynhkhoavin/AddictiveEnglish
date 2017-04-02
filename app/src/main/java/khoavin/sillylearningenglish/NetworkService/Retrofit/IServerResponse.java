package khoavin.sillylearningenglish.NetworkService.Retrofit;

import com.android.volley.NetworkError;

public interface IServerResponse<T> {
    //The success response
    void onSuccess(T responseObj);

    //The error response
    void onError(SillyError sillyError);
}

