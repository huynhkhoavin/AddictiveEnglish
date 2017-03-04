package khoavin.sillylearningenglish.EventListener;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/28/2017.
 */

public interface GetFriendListEvent {
    void getAllFriends(FirebaseAccount[] userFriends);
}
