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
    private Object luSequence;

    @SerializedName("ls_id")
    @Expose
    private String lsId;

    @SerializedName("lu_url")
    @Expose
    private String luUrl;

    @SerializedName("lu_type")
    @Expose
    private Object luType;

    @SerializedName("lu_duration")
    @Expose
    private String luDuration;

    int currentProgressUnit;

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

    public Object getLuSequence() {
        return luSequence;
    }

    public void setLuSequence(Object luSequence) {
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

    public Object getLuType() {
        return luType;
    }

    public void setLuType(Object luType) {
        this.luType = luType;
    }

    public String getLuDuration() {
        return luDuration;
    }

    public void setLuDuration(String luDuration) {
        this.luDuration = luDuration;
    }

    public void setCurrentProgressUnit(int currentProgressUnit) {
        this.currentProgressUnit = currentProgressUnit;
    }

    public int getCurrentProgressUnit() {
        return currentProgressUnit;
    }

    public boolean getActiveState(){
        return (Integer.parseInt(luId)<=currentProgressUnit);
    }
}