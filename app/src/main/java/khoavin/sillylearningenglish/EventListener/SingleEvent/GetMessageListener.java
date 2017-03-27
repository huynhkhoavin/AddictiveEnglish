package khoavin.sillylearningenglish.EventListener.SingleEvent;

import java.util.ArrayList;

/**
 * Created by Khoavin on 3/11/2017.
 */

public interface GetMessageListener {
    void OnSuccess(ArrayList<String> listMessage,ArrayList<String> listKeys);
    void OnError(String Error);
}
