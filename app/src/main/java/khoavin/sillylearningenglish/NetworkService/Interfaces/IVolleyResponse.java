package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;

/**
 * Created by OatOal on 5/13/2017.
 */

public interface IVolleyResponse<T>
{
    /**
     * The success response
     * @param responseObj
     */
    void onSuccess(T responseObj);

    /**
     * The error response
     * @param errorCode
     */
    void onError(ErrorCode errorCode);
}
