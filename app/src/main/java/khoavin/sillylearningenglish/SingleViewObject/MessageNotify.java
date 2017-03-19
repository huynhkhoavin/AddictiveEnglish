package khoavin.sillylearningenglish.SingleViewObject;

/**
 * Created by Khoavin on 3/18/2017.
 */

public class MessageNotify {
    String SenderUid;
    int UnreadMessage;

    public MessageNotify(String senderUid, int unreadMessage) {
        SenderUid = senderUid;
        UnreadMessage = unreadMessage;
    }

    public String getSenderUid() {
        return SenderUid;
    }

    public void setSenderUid(String senderUid) {
        SenderUid = senderUid;
    }

    public int getUnreadMessage() {
        return UnreadMessage;
    }

    public void setUnreadMessage(int unreadMessage) {
        UnreadMessage = unreadMessage;
    }
}
