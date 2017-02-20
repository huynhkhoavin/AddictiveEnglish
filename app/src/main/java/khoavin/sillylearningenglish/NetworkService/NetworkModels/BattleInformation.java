package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BattleInformation {

    //region Properties

    //the battle id
    @SerializedName("battle_id")
    @Expose
    private Integer battleId;

    //the question list
    @SerializedName("question_list")
    @Expose
    private List<Question> questionList = null;

    //endregion

    //region Get and set properties

    public Integer getBattleId() {
        return battleId;
    }

    public void setBattleId(Integer battleId) {
        this.battleId = battleId;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    //endregion
}