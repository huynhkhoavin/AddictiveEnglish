package khoavin.sillylearningenglish.EventListener.SingleEvent;

import khoavin.sillylearningenglish.SingleObject.Friend;

/**
 * Created by Khoavin on 3/12/2017.
 */

public interface FriendActionListener {
    void ChatAction(int position, Friend friend);
    void ChallengeAction(int position, Friend friend);
    void GetInfoAction(int position, Friend friend);
    void UnFriend(int position, Friend friend);
}
