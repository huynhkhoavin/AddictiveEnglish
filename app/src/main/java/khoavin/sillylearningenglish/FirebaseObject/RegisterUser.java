package khoavin.sillylearningenglish.FirebaseObject;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class RegisterUser {
    private String uid;
    private String email;
    private String firebaseToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RegisterUser() {
    }

    public RegisterUser(String uid, String email, String firebaseToken, String name) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
        this.name = name;
    }
}
