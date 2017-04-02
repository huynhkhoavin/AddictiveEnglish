package khoavin.sillylearningenglish.NetworkService.NetworkModels;

/**
 * Created by Khoavin on 4/2/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesson {

    @SerializedName("ls_id")
    @Expose
    private String lsId;
    @SerializedName("ls_title")
    @Expose
    private String lsTitle;
    @SerializedName("ls_level")
    @Expose
    private String lsLevel;
    @SerializedName("ls_chapter")
    @Expose
    private String lsChapter;
    @SerializedName("ls_source")
    @Expose
    private String lsSource;
    @SerializedName("ls_price")
    @Expose
    private String lsPrice;
    @SerializedName("ls_exp")
    @Expose
    private String lsExp;
    @SerializedName("ls_max_hear_times")
    @Expose
    private String lsMaxHearTimes;
    @SerializedName("ls_url")
    @Expose
    private String lsUrl;
    @SerializedName("ls_download")
    @Expose
    private Object lsDownload;
    @SerializedName("ls_note")
    @Expose
    private String lsNote;

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

    public String getLsLevel() {
        return lsLevel;
    }

    public void setLsLevel(String lsLevel) {
        this.lsLevel = lsLevel;
    }

    public String getLsChapter() {
        return lsChapter;
    }

    public void setLsChapter(String lsChapter) {
        this.lsChapter = lsChapter;
    }

    public String getLsSource() {
        return lsSource;
    }

    public void setLsSource(String lsSource) {
        this.lsSource = lsSource;
    }

    public String getLsPrice() {
        return lsPrice;
    }

    public void setLsPrice(String lsPrice) {
        this.lsPrice = lsPrice;
    }

    public String getLsExp() {
        return lsExp;
    }

    public void setLsExp(String lsExp) {
        this.lsExp = lsExp;
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

    public Object getLsDownload() {
        return lsDownload;
    }

    public void setLsDownload(Object lsDownload) {
        this.lsDownload = lsDownload;
    }

    public String getLsNote() {
        return lsNote;
    }

    public void setLsNote(String lsNote) {
        this.lsNote = lsNote;
    }

}