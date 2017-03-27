package khoavin.sillylearningenglish.Function.Friend.ChatObject;

/**
 * Created by Khoavin on 3/22/2017.
 */

public class ChatUnit{
    boolean isFriend;
    String Mesage;

    public ChatUnit(boolean isFriend, String mesage) {
        this.isFriend = isFriend;
        Mesage = mesage;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public String getMesage() {
        return Mesage;
    }

    public void setMesage(String mesage) {
        Mesage = mesage;
    }
}