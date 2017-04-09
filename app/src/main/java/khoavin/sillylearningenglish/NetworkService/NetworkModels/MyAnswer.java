package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import khoavin.sillylearningenglish.SingleViewObject.Common;

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

    public Common.AnswerKey getAnswer() {
        if (answer.equals("1"))
            return Common.AnswerKey.A;
        if (trueAnswer.equals("2"))
            return Common.AnswerKey.B;
        if (answer.equals("3"))
            return Common.AnswerKey.C;
        if (answer.equals("4"))
            return Common.AnswerKey.D;
        return Common.AnswerKey.U;
    }

    public void setAnswer(Integer answer) {
        this.answer = String.valueOf(answer);
    }

    public Common.AnswerKey getTrueAnswer() {
        if (trueAnswer.equals("1"))
            return Common.AnswerKey.A;
        if (trueAnswer.equals("2"))
            return Common.AnswerKey.B;
        if (trueAnswer.equals("3"))
            return Common.AnswerKey.C;
        if (trueAnswer.equals("4"))
            return Common.AnswerKey.D;
        return Common.AnswerKey.U;
    }

    public void setTrueAnswer(Integer trueAnswer) {
        this.trueAnswer = String.valueOf(trueAnswer);
    }

}