package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KhoaVin on 06/07/2017.
 */

public class Reward {

    @SerializedName("reward_id")
    @Expose
    private String rewardId;
    @SerializedName("reward_type")
    @Expose
    private String rewardType;
    @SerializedName("reward_value")
    @Expose
    private String rewardValue;
    @SerializedName("reward_name")
    @Expose
    private String rewardName;
    @SerializedName("reward_begin")
    @Expose
    private String rewardBegin;
    @SerializedName("reward_end")
    @Expose
    private String rewardEnd;
    @SerializedName("ls_id")
    @Expose
    private String lsId;

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(String rewardValue) {
        this.rewardValue = rewardValue;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardBegin() {
        return rewardBegin;
    }

    public void setRewardBegin(String rewardBegin) {
        this.rewardBegin = rewardBegin;
    }

    public String getRewardEnd() {
        return rewardEnd;
    }

    public void setRewardEnd(String rewardEnd) {
        this.rewardEnd = rewardEnd;
    }

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

}