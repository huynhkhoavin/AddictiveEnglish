package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    //region WebModel properties

    //The user's name
    @SerializedName("user_name")
    @Expose
    private String userName;

    //The user's id
    @SerializedName("user_id")
    @Expose
    private String userId;

    //The user's winrate
    @SerializedName("win_rate")
    @Expose
    private Double winRate;

    //The user's total battle
    @SerializedName("total_battle")
    @Expose
    private Integer totalBattle;

    //The user's avatar url
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    //The user's current coin
    @SerializedName("coins")
    @Expose
    private Integer coins;

    //The user's battle chains state
    @SerializedName("battle_chains")
    @Expose
    private List<Integer> battleChains = null;

    //The user's ranking infromation
    @SerializedName("ranking")
    @Expose
    private Ranking ranking;

    //endregion

    //region Gets and sets Webmodel properties

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getWinRate() {
        return winRate;
    }

    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }

    public Integer getTotalBattle() {
        return totalBattle;
    }

    public void setTotalBattle(Integer totalBattle) {
        this.totalBattle = totalBattle;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coint) {
        this.coins = coint;
    }

    public List<Integer> getBattleChains() {
        return battleChains;
    }

    public void setBattleChains(List<Integer> battleChains) {
        this.battleChains = battleChains;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    //endregion

}