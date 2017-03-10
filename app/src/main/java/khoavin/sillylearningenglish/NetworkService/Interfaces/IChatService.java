package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.EventListener.SingleEvent.SendMessageListener;

/**
 * Created by Khoavin on 3/11/2017.
 */

public interface IChatService {
    void sendMessageToUid(String senderUid, String receiverUid, String message, SendMessageListener sendMessageListener);
    void getNewMessage();
}
