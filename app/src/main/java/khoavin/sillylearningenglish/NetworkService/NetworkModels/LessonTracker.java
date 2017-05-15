package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KhoaVin on 13/05/2017.
 */

public class LessonTracker {

    @SerializedName("lst_id")
    @Expose
    private String lstId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("lu_id")
    @Expose
    private String luId;
    @SerializedName("ls_id")
    @Expose
    private String lsId;
    @SerializedName("lst_update_time")
    @Expose
    private String lstUpdateTime;
    @SerializedName("lst_current_listen")
    @Expose
    private String lstCurrentListen;

    public String getLstId() {
        return lstId;
    }

    public void setLstId(String lstId) {
        this.lstId = lstId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLuId() {
        return luId;
    }

    public void setLuId(String luId) {
        this.luId = luId;
    }

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public String getLstUpdateTime() {
        return lstUpdateTime;
    }

    public void setLstUpdateTime(String lstUpdateTime) {
        this.lstUpdateTime = lstUpdateTime;
    }

    public String getLstCurrentListen() {
        return lstCurrentListen;
    }

    public void setLstCurrentListen(String lstCurrentListen) {
        this.lstCurrentListen = lstCurrentListen;
    }

}