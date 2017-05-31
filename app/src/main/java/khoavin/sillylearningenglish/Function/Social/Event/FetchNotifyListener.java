package khoavin.sillylearningenglish.Function.Social.Event;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;

/**
 * Created by Dev02 on 5/31/2017.
 */

public interface FetchNotifyListener {
    void onFetchSuccess(ArrayList<Notification> listNotify);
    void onFetchFailed(String errorMessage);
}
