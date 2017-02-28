package khoavin.sillylearningenglish.EventListener;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseUser;

/**
 * Created by KhoaVin on 2/26/2017.
 */

public interface FriendEvent {
    void findUser(FirebaseUser firebaseUser);
    void getAllFriends(FirebaseUser[] firebaseUsers);
}
