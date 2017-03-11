package khoavin.sillylearningenglish.FirebaseObject;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Khoavin on 3/11/2017.
 */

@IgnoreExtraProperties
public class FirebaseChat {
    public String sender;
    public String receiver;
    public String senderUid;
    public String receiverUid;
    public String message;
    public long timestamp;

    public FirebaseChat() {
    }

    public FirebaseChat(String sender, String receiver, String senderUid, String receiverUid, String message, long timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.message = message;
        this.timestamp = timestamp;
    }
}