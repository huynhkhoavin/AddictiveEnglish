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

    public String getName() {
        return name;
    }

    public Integer getRank() {
        return Integer.valueOf(rank);
    }

    public Integer getLevel() {
        return Integer.valueOf(level);
    }

    public Integer getTotalMatch() {
        return Integer.valueOf(totalMatch);
    }

    public Integer getWinMatch() {
        return Integer.valueOf(winMatch);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Convert from ranking item to enemy item.
     * @param item
     */
    public void FromRankingItem(Ranking item)
    {
        userId = item.getUserId();
        name = item.getUserName();
        rank = String.valueOf(item.getRankPosition());
        level = String.valueOf(item.getLevel());
        totalMatch = String.valueOf(item.getTotalBattle());
        winMatch = String.valueOf(item.getWinBattle());
        avatarUrl = item.getUserAvatar();
    }

}