package khoavin.sillylearningenglish.EventListener.GlobalEvent;

import android.util.Log;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEventListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.PersonalEventListener;

/**
 * Created by Dev02 on 3/1/2017.
 */

public class  GlobalEvent {
    public static GlobalEvent instance;
    public static ArrayList<FriendEventListener> friendEventListeners;
    public static ArrayList<PersonalEventListener> personalEventListeners;
    public static GlobalEvent getInstance(){
        if (instance==null)
        {
            instance = new GlobalEvent();
        }
        return instance;
    }
    private GlobalEvent(){
        friendEventListeners = new ArrayList<FriendEventListener>();
        personalEventListeners = new ArrayList<PersonalEventListener>();
        Log.e("Global Event","hahaha");
    }
}
