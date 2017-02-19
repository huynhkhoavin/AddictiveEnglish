package khoavin.sillylearningenglish.SERVICE.WebModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OatOal on 2/19/2017.
 */

public class Ranking {

    //region Ranking WebModel properties

    //The user's medal
    @SerializedName("medal")
    @Expose
    private Integer medal;

    //The user's rank position
    @SerializedName("rank_position")
    @Expose
    private Integer rankPosition;

    //The user's rank level
    @SerializedName("rank_level")
    @Expose
    private Integer rankLevel;

    //endregion

    //region Gets or sets ranking webmodel properties

    public Integer getMedal() {
        return medal;
    }

    public void setMedal(Integer medal) {
        this.medal = medal;
    }

    public Integer getRankPosition() {
        return rankPosition;
    }

    public void setRankPosition(Integer rankPosition) {
        this.rankPosition = rankPosition;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    //endregion

}