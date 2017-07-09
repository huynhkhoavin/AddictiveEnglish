
package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import khoavin.sillylearningenglish.SingleViewObject.Common;

public class Question {

    @SerializedName("battle_id")
    @Expose
    private String battleId;
    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("question_content")
    @Expose
    private String questionContent;
    @SerializedName("audio_source")
    @Expose
    private String audioSource;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("answer_a")
    @Expose
    private String answerA;
    @SerializedName("answer_b")
    @Expose
    private String answerB;

    /**
     * Check if current answer was answered.
     */
    private boolean isAnswered = false;

    public void setQuestionWasAnswered(){
        isAnswered = true;
    }

    public boolean wasAnswered(){
        return isAnswered;
    }

    //My answer
    private MyAnswer myAnswer;

    public Integer getBattleId() {
        return Integer.valueOf(battleId);
    }

    public Integer getQuestionId() {
        return Integer.valueOf(questionId);
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public String getAudioSource() {
        return audioSource;
    }

    public Common.QuestionType getQuestionType() {
        if (questionType.equals("1"))
            return Common.QuestionType.READING;
        else
        if (questionType.equals("2"))
            return Common.QuestionType.LISTENING;
        else
            return Common.QuestionType.NOT_FOUND;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setMyAnswer(MyAnswer myAnswer)
    {
        this.myAnswer = myAnswer;
    }

    public MyAnswer getMyAnswer()
    {
        return myAnswer;
    }

}