package khoavin.sillylearningenglish.Function.Friend.ChatObject;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Khoavin on 3/22/2017.
 */

public class ChatRoom {

    ImageView FriendAvatar;
    ArrayList<ChatUnit> listChatStore;

    public ChatRoom(ImageView friendAvatar) {
        FriendAvatar = friendAvatar;
        listChatStore = new ArrayList<>();
    }

    public ChatRoom() {
    }

    public ImageView getFriendAvatar() {
        return FriendAvatar;
    }

    public void setFriendAvatar(ImageView friendAvatar) {
        FriendAvatar = friendAvatar;
    }

    public ArrayList<ChatUnit> getListChatStore() {
        return listChatStore;
    }

    public void setListChatStore(ArrayList<ChatUnit> listChatStore) {
        this.listChatStore = listChatStore;
    }
}
