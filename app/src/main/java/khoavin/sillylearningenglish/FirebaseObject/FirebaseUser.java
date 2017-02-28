package khoavin.sillylearningenglish.FirebaseObject;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public class FirebaseUser {
    private String uid;
    private String email;
    private String firebaseToken;
    private String name;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    private String avatarUrl;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


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

    public FirebaseUser() {
    }

    public FirebaseUser(String uid, String email, String firebaseToken, String name, String avatarUrl) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }
}
