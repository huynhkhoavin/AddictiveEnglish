package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.EventListener.SingleEvent.GetMessageListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.SendMessageListener;

/**
 * Created by Khoavin on 3/11/2017.
 */

public interface IChatService {
    void sendMessageToUid(String senderUid, String receiverUid, String message, SendMessageListener sendMessageListener);
    void getMessageFromUid(String Uid, GetMessageListener getMessageListener);
    void setReadMessageFromUid(String Uid,String ChatKey);
    void removeListener(String Uid);
}
