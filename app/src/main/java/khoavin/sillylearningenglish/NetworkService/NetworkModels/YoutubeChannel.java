package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KhoaVin on 08/07/2017.
 */

public class YoutubeChannel {

    @SerializedName("yc_id")
    @Expose
    private String ycId;
    @SerializedName("yc_name")
    @Expose
    private String ycName;
    @SerializedName("yc_code")
    @Expose
    private String ycCode;
    @SerializedName("yc_url")
    @Expose
    private String ycUrl;

    public String getYcId() {
        return ycId;
    }

    public void setYcId(String ycId) {
        this.ycId = ycId;
    }

    public String getYcName() {
        return ycName;
    }

    public void setYcName(String ycName) {
        this.ycName = ycName;
    }

    public String getYcCode() {
        return ycCode;
    }

    public void setYcCode(String ycCode) {
        this.ycCode = ycCode;
    }

    public String getYcUrl() {
        return ycUrl;
    }

    public void setYcUrl(String ycUrl) {
        this.ycUrl = ycUrl;
    }

}