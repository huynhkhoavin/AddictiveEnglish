package khoavin.sillylearningenglish.Function.Arena.Views;

import khoavin.sillylearningenglish.SingleObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IAnswerView {

    void SetTimeProgressMaxValue(long maxValue);

    void SetTimeProgressValue(long value);

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
