package khoavin.sillylearningenglish.NetworkService.NetworkModels;

/**
 * Created by OatOal on 3/25/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leaderboard {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("win_match")
    @Expose
    private String winMatch;
    @SerializedName("total_match")
    @Expose
    private String totalMatch;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

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

    public Integer getWinMatch() {
        return Integer.valueOf(winMatch);
    }

    public void setWinMatch(Integer winMatch) {
        this.winMatch = String.valueOf(winMatch);
    }

    public Integer getTotalMatch() {
        return Integer.valueOf(totalMatch);
    }

    public void setTotalMatch(Integer totalMatch) {
        this.totalMatch = String.valueOf(totalMatch);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}