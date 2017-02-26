package khoavin.sillylearningenglish.FUNCTION.Friend.View;

import khoavin.sillylearningenglish.FirebaseObject.RegisterUser;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public interface IFriendListView {
    void BindingUI();
    void ShowFriendList(Friend[] friends);
    void ReloadFriendList();
    void displaySearchedUser(RegisterUser user);
}
