package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dev02 on 5/31/2017.
 */

public class Notification {

    @SerializedName("notify_id")
    @Expose
    private String notifyId;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_location")
    @Expose
    private String userLocation;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("notify_content")
    @Expose
    private String notifyContent;
    @SerializedName("like_count")
    @Expose
    private String likeCount;
    @SerializedName("comment_count")
    @Expose
    private String commentCount;

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

}