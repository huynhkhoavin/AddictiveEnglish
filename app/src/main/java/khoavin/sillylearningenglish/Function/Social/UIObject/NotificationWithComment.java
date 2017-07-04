package khoavin.sillylearningenglish.Function.Social.UIObject;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Comment;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;

/**
 * Created by KhoaVin on 03/07/2017.
 */

public class NotificationWithComment {
    Notification notification;
    ArrayList<Comment> listComment;

    public NotificationWithComment(Notification notification, ArrayList<Comment> listComment) {
        this.notification = notification;
        this.listComment = listComment;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public ArrayList<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(ArrayList<Comment> listComment) {
        this.listComment = listComment;
    }
}
