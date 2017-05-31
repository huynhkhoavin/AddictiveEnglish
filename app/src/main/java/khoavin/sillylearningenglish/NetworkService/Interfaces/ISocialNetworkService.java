package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.app.Activity;

import khoavin.sillylearningenglish.Function.Social.Event.FetchNotifyListener;
import khoavin.sillylearningenglish.Function.Social.Event.PostNotifyListener;

/**
 * Created by Dev02 on 5/31/2017.
 */

public interface ISocialNetworkService {
    void Init(Activity activity);
    void postNotification(String notificationContent, PostNotifyListener postNotifyListener);
    void getHomeNotification(FetchNotifyListener fetchNotifyListener);
    void getProfileNotification(FetchNotifyListener fetchNotifyListener);
    void getProfile(FetchNotifyListener fetchNotifyListener);
}
