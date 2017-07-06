package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KhoaVin on 05/07/2017.
 */

public class SourceUnit {

    @SerializedName("lsu_id")
    @Expose
    private String lsuId;
    @SerializedName("lsu_source")
    @Expose
    private String lsuSource;
    @SerializedName("lsu_level")
    @Expose
    private String lsuLevel;
    @SerializedName("lsu_name")
    @Expose
    private String lsuName;

    public String getLsuId() {
        return lsuId;
    }

    public void setLsuId(String lsuId) {
        this.lsuId = lsuId;
    }

    public String getLsuSource() {
        return lsuSource;
    }

    public void setLsuSource(String lsuSource) {
        this.lsuSource = lsuSource;
    }

    public String getLsuLevel() {
        return lsuLevel;
    }

    public void setLsuLevel(String lsuLevel) {
        this.lsuLevel = lsuLevel;
    }

    public String getLsuName() {
        return lsuName;
    }

    public void setLsuName(String lsuName) {
        this.lsuName = lsuName;
    }

}