package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OatOal on 6/27/2017.
 */

public class ChainInfo {
    @SerializedName("battle_id")
    @Expose
    private String battleId;
    @SerializedName("date_create")
    @Expose
    private String dateCreate;
    @SerializedName("victory_id")
    @Expose
    private String victoryId;

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getVictoryId() {
        return victoryId;
    }

    public void setVictoryId(String victoryId) {
        this.victoryId = victoryId;
    }

}
