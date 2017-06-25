package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OatOal on 6/22/2017.
 */

public class BattleHistoryInfo {
    @SerializedName("battle_id")
    @Expose
    private String battleId;
    @SerializedName("attacker_id")
    @Expose
    private String attackerId;
    @SerializedName("defender_id")
    @Expose
    private String defenderId;
    @SerializedName("date_create")
    @Expose
    private String dateCreate;
    @SerializedName("attacker_begin_time")
    @Expose
    private String attackerBeginTime;
    @SerializedName("defender_begin_time")
    @Expose
    private String defenderBeginTime;
    @SerializedName("attacker_end_time")
    @Expose
    private String attackerEndTime;
    @SerializedName("defender_end_time")
    @Expose
    private String defenderEndTime;
    @SerializedName("victory_id")
    @Expose
    private String victoryId;
    @SerializedName("attacker_name")
    @Expose
    private String attackerName;
    @SerializedName("defender_name")
    @Expose
    private String defenderName;
    @SerializedName("attacker_avatar")
    @Expose
    private String attackerAvatar;
    @SerializedName("defender_avatar")
    @Expose
    private String defenderAvatar;
    @SerializedName("total_attacker_answer")
    @Expose
    private String totalAttackerAnswer;
    @SerializedName("total_defender_answer")
    @Expose
    private String totalDefenderAnswer;

    public String getBattleId() {
        return battleId;
    }

    public String getAttackerId() {
        return attackerId;
    }

    public String getDefenderId() {
        return defenderId;
    }

    public Date getDateCreate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = dateFormat.parse(dateCreate);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }
        return d;
    }

    public Date getAttackerBeginTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = dateFormat.parse(attackerBeginTime);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }
        return d;
    }

    public Date getDefenderBeginTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = dateFormat.parse(defenderBeginTime);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }
        return d;
    }

    public Date getAttackerEndTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = dateFormat.parse(attackerEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }
        return d;
    }

    public Date getDefenderEndTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = dateFormat.parse(defenderEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }
        return d;
    }

    public String getVictoryId() {
        return victoryId;
    }

    public String getAttackerName() {
        return attackerName;
    }

    public String getDefenderName() {
        return defenderName;
    }

    public String getAttackerAvatar() {
        return attackerAvatar;
    }

    public String getDefenderAvatar() {
        return defenderAvatar;
    }

    public String getTotalAttackerAnswer() {
        return totalAttackerAnswer;
    }

    public String getTotalDefenderAnswer() {
        return totalDefenderAnswer;
    }
}
