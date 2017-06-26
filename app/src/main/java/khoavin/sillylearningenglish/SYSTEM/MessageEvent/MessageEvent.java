package khoavin.sillylearningenglish.SYSTEM.MessageEvent;

/**
 * Created by Khoavin on 4/9/2017.
 */

public class MessageEvent{

    public String mMessage;
    public String Url;
    public int Value;

    public String getUrl() {
        return Url;
    }

    public MessageEvent(String mMessage, int value) {
        this.mMessage = mMessage;
        Value = value;
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

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }
}