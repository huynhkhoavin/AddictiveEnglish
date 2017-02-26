package khoavin.sillylearningenglish.FUNCTION.Friend.Presenter;

import khoavin.sillylearningenglish.EventListener.FirebaseEventListener;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IFriendPresenter {
    void ShowFriendList();
    void setFirebaseEventListener(FirebaseEventListener firebaseEventListener);
    void searchUser(String username);
}
