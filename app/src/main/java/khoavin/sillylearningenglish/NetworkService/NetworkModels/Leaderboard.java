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