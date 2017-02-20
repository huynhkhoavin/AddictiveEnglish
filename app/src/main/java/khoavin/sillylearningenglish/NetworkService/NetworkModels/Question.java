package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import khoavin.sillylearningenglish.SINGLE_OBJECT.Common;

public class Question {

    //region Question properties

    //the question id
    @SerializedName("question_id")
    @Expose
    private Integer questionId;

    //the question title
    @SerializedName("question_title")
    @Expose
    private String questionTitle;

    //the question type
    @SerializedName("question_type")
    @Expose
    private Integer questionType;

    //the question content
    @SerializedName("question_content")
    @Expose
    private String questionContent;

    //the audio source
    @SerializedName("audio_source")
    @Expose
    private String audioSource;

    //the answer a content
    @SerializedName("answer_a")
    @Expose
    private String answerA;

    //the answer b content
    @SerializedName("answer_b")
    @Expose
    private String answerB;

    //endregion

    //region Get and set properties

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(String audioSource) {
        this.audioSource = audioSource;
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

    //convert question type Integer to Enum
    public Common.QuestionType getQuestionEnumType() {
        switch (questionType) {
            case 0:
                return Common.QuestionType.Reading;
            case 1:
                return Common.QuestionType.Listening;
            default:
                return Common.QuestionType.Unknow;
        }
    }

    //endregion

}