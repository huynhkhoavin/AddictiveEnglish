package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by OatOal on 6/20/2017.
 */

public class BattleHistory implements Serializable {
    private String userAvatar;
    private String userName;
    private String userTrueAnswer;
    private long userTotalTime;

    private String enemyAvatar;
    private String enemyName;
    private String enemyTrueAnswer;
    private Long enemyTotalTime;

    private boolean isVictoryBattle;

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    private Date dateCreate;

    public boolean isVictoryBattle() {
        return isVictoryBattle;
    }

    public void setVictoryBattle(boolean victoryBattle) {
        isVictoryBattle = victoryBattle;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTrueAnswer() {
        return userTrueAnswer;
    }

    public void setUserTrueAnswer(String userTrueAnswer) {
        this.userTrueAnswer = userTrueAnswer;
    }

    public Long getUserTotalTime() {
        return userTotalTime;
    }

    public void setUserTotalTime(Long userTotalTime) {
        this.userTotalTime = userTotalTime;
    }

    public String getEnemyAvatar() {
        return enemyAvatar;
    }

    public void setEnemyAvatar(String enemyAvatar) {
        this.enemyAvatar = enemyAvatar;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public String getEnemyTrueAnswer() {
        return enemyTrueAnswer;
    }

    public void setEnemyTrueAnswer(String enemyTrueAnswer) {
        this.enemyTrueAnswer = enemyTrueAnswer;
    }

    public Long getEnemyTotalTime() {
        return enemyTotalTime;
    }

    public void setEnemyTotalTime(Long enemyTotalTime) {
        this.enemyTotalTime = enemyTotalTime;
    }
}
