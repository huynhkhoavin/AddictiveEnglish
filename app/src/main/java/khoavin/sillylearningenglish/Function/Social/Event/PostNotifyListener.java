package khoavin.sillylearningenglish.Function.Social.Event;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;

/**
 * Created by Dev02 on 5/31/2017.
 */

public interface PostNotifyListener {
    void onPostSuccess(Notification notification);
    void onPostError(String ErrorMessage);
}
