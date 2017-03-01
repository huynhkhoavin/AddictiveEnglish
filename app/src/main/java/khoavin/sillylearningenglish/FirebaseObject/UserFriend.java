package khoavin.sillylearningenglish.FirebaseObject;

/**
 * Created by KhoaVin on 2/28/2017.
 */

public class UserFriend extends FirebaseAccount {
    private FirebaseAccount account;
    private boolean OnlineStatus;

    public UserFriend(FirebaseAccount account, boolean onlineStatus){
        this.account = account;
        this.OnlineStatus = onlineStatus;
    }

    public FirebaseAccount getAccount() {
        return account;
    }

    public void setAccount(FirebaseAccount account) {
        this.account = account;
    }

    public boolean isOnlineStatus() {
        return OnlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        OnlineStatus = onlineStatus;
    }
}
