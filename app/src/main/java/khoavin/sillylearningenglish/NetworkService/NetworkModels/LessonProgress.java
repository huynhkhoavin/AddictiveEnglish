package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KhoaVin on 11/07/2017.
 */

public class LessonProgress {

    @SerializedName("ls_id")
    @Expose
    private String lsId;
    @SerializedName("ls_title")
    @Expose
    private String lsTitle;
    @SerializedName("ls_avatar_url")
    @Expose
    private String lsAvatarUrl;
    @SerializedName("ls_progress")
    @Expose
    private String lsProgress;
    @SerializedName("total_progress")
    @Expose
    private String totalProgress;

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public String getLsTitle() {
        return lsTitle;
    }

    public void setLsTitle(String lsTitle) {
        this.lsTitle = lsTitle;
    }

    public String getLsAvatarUrl() {
        return lsAvatarUrl;
    }

    public void setLsAvatarUrl(String lsAvatarUrl) {
        this.lsAvatarUrl = lsAvatarUrl;
    }

    public float getLsProgress() {
        return Float.parseFloat(lsProgress);
    }

    public void setLsProgress(String lsProgress) {
        this.lsProgress = lsProgress;
    }

    public float getTotalProgress() {
        return Float.parseFloat(totalProgress);
    }

    public void setTotalProgress(String totalProgress) {
        this.totalProgress = totalProgress;
    }
}