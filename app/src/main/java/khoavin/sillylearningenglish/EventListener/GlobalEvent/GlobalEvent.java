package khoavin.sillylearningenglish.EventListener.GlobalEvent;

import android.util.Log;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;
import khoavin.sillylearningenglish.EventListener.SingleEvent.PersonalEvent;

/**
 * Created by Dev02 on 3/1/2017.
 */

public class  GlobalEvent {
    public static GlobalEvent instance;
    public static ArrayList<FriendEvent> friendEvents;
    public static ArrayList<PersonalEvent> personalEvents;
    public static GlobalEvent getInstance(){
        if (instance==null)
        {
            instance = new GlobalEvent();
        }
        return instance;
    }
    private GlobalEvent(){
        friendEvents = new ArrayList<FriendEvent>();
        personalEvents = new ArrayList<PersonalEvent>();
        Log.e("Global Event","hahaha");
    }
}
