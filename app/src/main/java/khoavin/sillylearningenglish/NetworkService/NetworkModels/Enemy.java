package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Enemy {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("total_match")
    @Expose
    private String totalMatch;
    @SerializedName("win_match")
    @Expose
    private String winMatch;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return Integer.valueOf(rank);
    }

    public void setRank(Integer rank) {
        this.rank = String.valueOf(rank);
    }

    public Integer getLevel() {
        return Integer.valueOf(level);
    }

    public void setLevel(Integer level) {
        this.level = String.valueOf(level);
    }

    public Integer getTotalMatch() {
        return Integer.valueOf(totalMatch);
    }

    public void setTotalMatch(Integer totalMatch) {
        this.totalMatch = String.valueOf(totalMatch);
    }

    public String getWinMatch() {
        return winMatch;
    }

    public void setWinMatch(Integer winMatch) {
        this.winMatch = String.valueOf(winMatch);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}