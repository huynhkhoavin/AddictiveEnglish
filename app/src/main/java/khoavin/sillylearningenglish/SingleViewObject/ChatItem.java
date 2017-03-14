package khoavin.sillylearningenglish.SingleViewObject;

import android.graphics.drawable.Drawable;

import javax.annotation.Resource;

/**
 * Created by Khoavin on 3/13/2017.
 */

public class ChatItem {
    String avatarUrl;
    String chatContent;

    public ChatItem(String avatarUrl, String chatContent) {
        this.avatarUrl = avatarUrl;
        this.chatContent = chatContent;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}
