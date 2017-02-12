package khoavin.sillylearningenglish.ENTITY_DATABASE;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class Friend {
    public Friend(int avatar, String name, boolean online_Status) {
        Avatar = avatar;
        Name = name;
        Online_Status = online_Status;
    }

    public int Avatar;

    public boolean getOnline_Status() {
        return Online_Status;
    }

    public void setOnline_Status(boolean online_Status) {
        Online_Status = online_Status;
    }

    public int getAvatar() {
        return Avatar;
    }

    public void setAvatar(int avatar) {
        Avatar = avatar;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String Name;
    public boolean Online_Status;

}
