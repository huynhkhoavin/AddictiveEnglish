package khoavin.sillylearningenglish.FirebaseObject;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class User {
    public String uid;
    public String email;
    public String firebaseToken;
    public User() {
    }

    public User(String uid, String email, String firebaseToken) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
    }
}
