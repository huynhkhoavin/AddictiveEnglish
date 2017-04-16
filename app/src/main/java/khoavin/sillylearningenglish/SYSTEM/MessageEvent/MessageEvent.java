package khoavin.sillylearningenglish.SYSTEM.MessageEvent;

/**
 * Created by Khoavin on 4/9/2017.
 */

public class MessageEvent{

    public String mMessage;
    public String Url;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public MessageEvent(String message) {
        mMessage = message;
    }

    public MessageEvent(String mMessage, String url) {
        this.mMessage = mMessage;
        Url = url;
    }

    public String getMessage() {
        return mMessage;
    }
}