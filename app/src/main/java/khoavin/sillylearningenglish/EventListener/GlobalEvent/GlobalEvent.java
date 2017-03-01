package khoavin.sillylearningenglish.EventListener.GlobalEvent;

import android.util.Log;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendEvent;

/**
 * Created by Dev02 on 3/1/2017.
 */

public class GlobalEvent {
    public ArrayList<FriendEvent> friendEvents;
    public GlobalEvent(){
        friendEvents = new ArrayList<FriendEvent>();
        Log.e("Global Event","hahaha");
    }
}
