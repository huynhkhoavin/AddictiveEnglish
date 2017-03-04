package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    //the user's id
    @SerializedName("user_id")
    @Expose
    private String userId;

    //the user's name
    @SerializedName("name")
    @Expose
    private String name;

    //the user remain coins
    @SerializedName("coin")
    @Expose
    private String coin;

    //the user's rank
    @SerializedName("rank")
    @Expose
    private String rank;

    //the user's level
    @SerializedName("level")
    @Expose
    private String level;

    //the win match
    @SerializedName("win_match")
    @Expose
    private String winMatch;

    //the total match
    @SerializedName("total_match")
    @Expose
    private String totalMatch;

    //the avatar's url
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

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getWinMatch() {
        return winMatch;
    }

    public void setWinMatch(String winMatch) {
        this.winMatch = winMatch;
    }

    public String getTotalMatch() {
        return totalMatch;
    }

    public void setTotalMatch(String totalMatch) {
        this.totalMatch = totalMatch;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}