package khoavin.sillylearningenglish.EventListener;

import khoavin.sillylearningenglish.FirebaseObject.UserFriend;

/**
 * Created by KhoaVin on 2/28/2017.
 */

public interface GetFriendListEvent {
    void getAllFriends(UserFriend[] userFriends);
}
