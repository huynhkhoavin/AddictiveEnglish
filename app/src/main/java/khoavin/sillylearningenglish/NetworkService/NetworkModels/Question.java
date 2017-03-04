package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import khoavin.sillylearningenglish.SINGLE_OBJECT.Common;

public class Question {

    @SerializedName("battle_id")
    @Expose
    private String battleId;
    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("question_content")
    @Expose
    private String questionContent;
    @SerializedName("answer_a")
    @Expose
    private String answerA;
    @SerializedName("answer_b")
    @Expose
    private String answerB;
    @SerializedName("audio_source")
    @Expose
    private Object audioSource;

    public Integer getBattleId() {
        return Integer.valueOf(battleId);
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public Integer getQuestionId() {
        return Integer.valueOf(questionId);
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Common.QuestionType getQuestionType() {
        if (questionType.equals("1"))
            return Common.QuestionType.Reading;
        else
        if (questionType.equals("2"))
            return Common.QuestionType.Listening;
        else
            return Common.QuestionType.Unknow;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAudioSource() {
        if (audioSource == null)
            return "";
        else
            return String.valueOf(audioSource);
    }

    public void setAudioSource(Object audioSource) {
        this.audioSource = audioSource;
    }

}