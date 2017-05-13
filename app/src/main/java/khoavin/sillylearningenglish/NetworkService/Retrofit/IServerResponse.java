package khoavin.sillylearningenglish.NetworkService.Retrofit;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;

public interface IServerResponse<T> {

    //The success response
    void onSuccess(T responseObj);

    //The error response
    void onError(SillyError errorCode);
}

