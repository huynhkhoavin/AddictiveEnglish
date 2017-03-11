package khoavin.sillylearningenglish.FirebaseObject;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Khoavin on 3/11/2017.
 */

@IgnoreExtraProperties
public class FirebaseChat {
    public String message;
    public boolean read;

    public FirebaseChat() {
    }

    public FirebaseChat(String message, boolean read) {
        this.message = message;
        read = read;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}