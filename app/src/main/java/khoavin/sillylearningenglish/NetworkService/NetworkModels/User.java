package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("win_match")
    @Expose
    private String winMatch;
    @SerializedName("total_match")
    @Expose
    private String totalMatch;
    @SerializedName("chain_for_rank")
    @Expose
    private String chainForRank;
    @SerializedName("coin")
    @Expose
    private String coin;
    @SerializedName("current_battle")
    @Expose
    private String currentBattle;
    @SerializedName("chain_matchs")
    @Expose
    private String chainMatchs;

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Integer getLevel() {
        return Integer.valueOf(level);
    }

    public Integer getRank() {
        return Integer.valueOf(rank);
    }

    public Integer getWinMatch() {
        return Integer.valueOf(winMatch);
    }

    public Integer getTotalMatch() {
        return Integer.valueOf(totalMatch);
    }

    public String getChainForRank() {
        return chainForRank;
    }

    public Integer getCoin() {
        return  Integer.valueOf(coin);
    }

    public Integer getCurrentBattle() {
        return Integer.valueOf(currentBattle);
    }

    public Integer getChainMatchs() {
        return Integer.valueOf(chainMatchs);
    }

}