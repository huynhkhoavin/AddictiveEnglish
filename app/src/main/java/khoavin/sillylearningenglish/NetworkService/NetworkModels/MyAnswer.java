package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyAnswer {

    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("true_answer")
    @Expose
    private String trueAnswer;

    public Integer getQuestionId() {
        return Integer.valueOf(questionId);
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = String.valueOf(questionId);
    }

    public Integer getAnswer() {
        return Integer.valueOf(answer);
    }

    public void setAnswer(Integer answer) {
        this.answer = String.valueOf(answer);
    }

    public Integer getTrueAnswer() {
        return Integer.valueOf(trueAnswer);
    }

    public void setTrueAnswer(Integer trueAnswer) {
        this.trueAnswer = String.valueOf(trueAnswer);
    }

}