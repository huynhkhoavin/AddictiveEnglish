package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.PersonalEvent;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Chat;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Friend;

/**
 * Created by KhoaVin on 2/23/2017.
 */

public interface IFriendService {
    FirebaseAccount findFriendByName(String name);
    void getAlldFriendUid(FriendEvent friendEvent);
    void getListUserDetail(final ArrayList<String> listUserUid, final FriendEvent friendEvent);
}
