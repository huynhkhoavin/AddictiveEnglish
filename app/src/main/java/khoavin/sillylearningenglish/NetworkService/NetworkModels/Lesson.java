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

    @SerializedName("ls_file_url")
    @Expose
    private String lsFileUrl;

    @SerializedName("ls_rate_total")
    @Expose
    private String lsRateTotal;

    @SerializedName("ls_rate_person")
    @Expose
    private String lsRatePerson;

    @SerializedName("ls_source")
    @Expose
    private String lsSource;

    public String getLsSource() {
        return lsSource;
    }

    public void setLsSource(String lsSource) {
        this.lsSource = lsSource;
    }

    @SerializedName("ls_author")
    @Expose
    private String lsAuthor;

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

    public String getLsFileUrl() {
        return lsFileUrl;
    }

    public void setLsFileUrl(String lsFileUrl) {
        this.lsFileUrl = lsFileUrl;
    }

    public float getLsRateTotal() {
        return Float.parseFloat(lsRateTotal);
    }

    public void setLsRateTotal(String lsRateTotal) {
        this.lsRateTotal = lsRateTotal;
    }

    public int getLsRatePerson() {
        return Integer.parseInt(lsRatePerson);
    }

    public void setLsRatePerson(int lsRatePerson) {
        this.lsRatePerson = String.valueOf(lsRatePerson);
    }

    public String getLsAuthor() {
        return lsAuthor;
    }

    public void setLsAuthor(String lsAuthor) {
        this.lsAuthor = lsAuthor;
    }
    public float getRate(){
        if(getLsRatePerson() == 0)
        {
            return ((float)getLsRateTotal());
        }
        else
            return ((float)getLsRateTotal())/((float)getLsRatePerson());
    }
}
