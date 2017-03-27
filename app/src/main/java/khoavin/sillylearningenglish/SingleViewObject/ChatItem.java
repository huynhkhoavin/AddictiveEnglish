package khoavin.sillylearningenglish.SingleViewObject;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import javax.annotation.Resource;

/**
 * Created by Khoavin on 3/13/2017.
 */

public class ChatItem {
    Bitmap avatarBitMap;
    String chatContent;

    public ChatItem(Bitmap avatar, String chatContent) {
        this.avatarBitMap = avatar;
        this.chatContent = chatContent;
    }

    public Bitmap getAvatarBitMap() {
        return avatarBitMap;
    }

    public void setAvatarBitMap(Bitmap avatarBitMap) {
        this.avatarBitMap = avatarBitMap;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}
