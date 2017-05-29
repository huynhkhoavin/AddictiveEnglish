package khoavin.sillylearningenglish.Function.Arena.Views;

import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IAnswerView {

    /**
     * Sets the percent of time passed
     * @param timePass
     */
    void setPercentOfTimePass(float timePass);

    /**
     * Set current times progress.
     * @param currentTime The current times.
     */
    void setCurrentTime(String currentTime);

    /**
     * Inform the result of selected answer.
     * @param answerKey The selected answer.
     * @param result The result of selected.
     */
    void informAnswerResult(Common.AnswerKey answerKey, boolean result);

    /**
     * Sets value for question title.
     * @param questionTitle The question title.
     */
    void setQuestionTitle(String questionTitle);

    /**
     * Sets value for question content.
     * @param questionContent The question content.
     */
    void setQuestionContent(String questionContent);

    /**
     * Sets value for answer A.
     * @param answerA The answer A.
     */
    void setAnswerA(String answerA);

    /**
     * Sets value for answer B.
     * @param answerB The answer B.
     */
    void setAnswerB(String answerB);

    /**
     * Sets question type.
     * @param questionType The question type.
     */
    void setQuestionType(Common.QuestionType questionType);

}
