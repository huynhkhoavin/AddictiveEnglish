package khoavin.sillylearningenglish.EventListener;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/26/2017.
 */

public interface FindUserEvent {
    void findUser(FirebaseAccount firebaseAccount);

}
