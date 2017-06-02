package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.app.Activity;

import khoavin.sillylearningenglish.NetworkService.EventListener.FetchNotifyListener;
import khoavin.sillylearningenglish.NetworkService.EventListener.PostNotifyListener;
import khoavin.sillylearningenglish.NetworkService.EventListener.CommentListener;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Comment;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;

/**
 * Created by Dev02 on 5/31/2017.
 */

public interface ISocialNetworkService {
    void Init(Activity activity);
    void postNotification(String notificationContent, PostNotifyListener postNotifyListener);
    void getHomeNotification(FetchNotifyListener fetchNotifyListener);
    void getProfileNotification(FetchNotifyListener fetchNotifyListener);
    void getProfile(FetchNotifyListener fetchNotifyListener);
    void doComment(Comment comment, CommentListener commentListener);
    void doLike(Notification notification);
}
