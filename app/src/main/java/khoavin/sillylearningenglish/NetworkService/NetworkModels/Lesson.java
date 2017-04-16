package khoavin.sillylearningenglish.NetworkService.NetworkModels;

/**
 * Created by Khoavin on 4/2/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Lesson implements Serializable {

    @SerializedName("ls_id")
    @Expose
    private String lsId;
    @SerializedName("ls_title")
    @Expose
    private String lsTitle;
    @SerializedName("ls_price")
    @Expose
    private String lsPrice;
    @SerializedName("ls_max_hear_times")
    @Expose
    private String lsMaxHearTimes;
    @SerializedName("ls_url")
    @Expose
    private String lsUrl;
    @SerializedName("ls_download")
    @Expose
    private String lsDownload;
    @SerializedName("ls_avatar_url")
    @Expose
    private String lsAvatarUrl;
    @SerializedName("ls_rate")
    @Expose
    private String lsRate;

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

    public String getLsPrice() {
        return lsPrice;
    }

    public void setLsPrice(String lsPrice) {
        this.lsPrice = lsPrice;
    }

    public String getLsMaxHearTimes() {
        return lsMaxHearTimes;
    }

    public void setLsMaxHearTimes(String lsMaxHearTimes) {
        this.lsMaxHearTimes = lsMaxHearTimes;
    }

    public String getLsUrl() {
        return lsUrl;
    }

    public void setLsUrl(String lsUrl) {
        this.lsUrl = lsUrl;
    }

    public String getLsDownload() {
        return lsDownload;
    }

    public void setLsDownload(String lsDownload) {
        this.lsDownload = lsDownload;
    }

    public String getLsAvatarUrl() {
        return lsAvatarUrl;
    }

    public void setLsAvatarUrl(String lsAvatarUrl) {
        this.lsAvatarUrl = lsAvatarUrl;
    }

    public String getLsRate() {
        return lsRate;
    }

    public void setLsRate(String lsRate) {
        this.lsRate = lsRate;
    }

}