package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Khoavin on 4/8/2017.
 */

public class LessonUnit {

    @SerializedName("lu_id")
    @Expose
    private String luId;
    @SerializedName("lu_name")
    @Expose
    private String luName;
    @SerializedName("lu_sequence")
    @Expose
    private String luSequence;
    @SerializedName("ls_id")
    @Expose
    private String lsId;
    @SerializedName("lu_url")
    @Expose
    private String luUrl;
    @SerializedName("lu_type")
    @Expose
    private String luType;

    public String getLuId() {
        return luId;
    }

    public void setLuId(String luId) {
        this.luId = luId;
    }

    public String getLuName() {
        return luName;
    }

    public void setLuName(String luName) {
        this.luName = luName;
    }

    public String getLuSequence() {
        return luSequence;
    }

    public void setLuSequence(String luSequence) {
        this.luSequence = luSequence;
    }

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public String getLuUrl() {
        return luUrl;
    }

    public void setLuUrl(String luUrl) {
        this.luUrl = luUrl;
    }

    public String getLuType() {
        return luType;
    }

    public void setLuType(String luType) {
        this.luType = luType;
    }

}