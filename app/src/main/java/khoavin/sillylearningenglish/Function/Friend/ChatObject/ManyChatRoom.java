package khoavin.sillylearningenglish.Function.Friend.ChatObject;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import khoavin.sillylearningenglish.EventListener.SingleEvent.ListItemAddListener;
import khoavin.sillylearningenglish.SingleViewObject.Friend;

/**
 * Created by Khoavin on 3/22/2017.
 */
//Chua tat ca cac dong chat lien quan tuong ung voi tung nguoi ban
public class ManyChatRoom {
    Map<String,ChatRoom> ListChatRoom;
    Context context;
    String currFriendUid;
    ListItemAddListener listItemAddListener;
    public ManyChatRoom(){
        ListChatRoom = new HashMap<>();
    }
    public ManyChatRoom(Context context) {
        ListChatRoom = new HashMap<>();
        this.context = context;
    }
    public void SetContext(Context context){
        this.context = context;
    }

    public Map<String, ChatRoom> getListChatRoom() {
        return ListChatRoom;
    }

    public String getCurrFriendUid() {
        return currFriendUid;
    }

    public void setCurrFriendUid(String Uid)
    {
        this.currFriendUid = Uid;
    }
    public boolean ChatRoomIsExist(String Uid)
    {
        if (ListChatRoom.get(Uid)==null)
            return false;
            return true;
    }

    public void setListItemAddListener(ListItemAddListener listItemAddListener)
    {
        this.listItemAddListener = listItemAddListener;
    }
    public void AddChatRoom(Friend friend)
    {
            ImageView friendAvatar = new ImageView(context);
            Glide.with(context)
                    .load(friend.getAvatar())
                    .into(friendAvatar);
            ListChatRoom.put(friend.getUid(),new ChatRoom(friendAvatar));
    }
    public void AddChatUnit(ChatUnit chatUnit)
    {
        if (!currFriendUid.equals("")){
            ListChatRoom.get(currFriendUid).getListChatStore().add(chatUnit);
        }
        if (listItemAddListener!=null)
            listItemAddListener.addItemEvent(currFriendUid,chatUnit);

    }
    public void AddChatUnits(boolean IsFriend,ArrayList<String> listMessage){
        for (String message : listMessage)
        {
            AddChatUnit(new ChatUnit(IsFriend,message));
        }
    }
}
