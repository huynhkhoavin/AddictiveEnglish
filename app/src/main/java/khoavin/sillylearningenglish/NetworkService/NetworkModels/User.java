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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
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

    public String getChainForRank() {
        return chainForRank;
    }

    public void setChainForRank(String chainForRank) {
        this.chainForRank = chainForRank;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCurrentBattle() {
        return currentBattle;
    }

    public void setCurrentBattle(String currentBattle) {
        this.currentBattle = currentBattle;
    }

    public String getChainMatchs() {
        return chainMatchs;
    }

    public void setChainMatchs(String chainMatchs) {
        this.chainMatchs = chainMatchs;
    }

}