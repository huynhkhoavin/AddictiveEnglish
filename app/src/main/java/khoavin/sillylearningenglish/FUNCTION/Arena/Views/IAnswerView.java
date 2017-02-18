package khoavin.sillylearningenglish.FUNCTION.Arena.Views;

import khoavin.sillylearningenglish.SINGLE_OBJECT.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IAnswerView {

    void SetTimeProgressMaxValue(float maxValue);

    void SetTimeProgressValue(float value);

    void SetQuestionTitle(String questionTitle);

    void SetQuestionContent(String questionContent);

    void SetAnswerForQuestionA(String answerA);

    void SetAnswerForQuestionB(String answerB);

    void ShowListeningIcon();

    void HideListeningIcon();

    void ShowRepeatIcon();

    void HideRepeatIcon();

    void HighlineTrueAnswer(Common.AnswerKey answerKey);

}
