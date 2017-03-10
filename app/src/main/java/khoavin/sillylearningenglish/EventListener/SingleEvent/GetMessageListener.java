package khoavin.sillylearningenglish.EventListener.SingleEvent;

/**
 * Created by Khoavin on 3/11/2017.
 */

public interface GetMessageListener {
    void OnSuccess(String Message);
    void OnError(String Error);
}
