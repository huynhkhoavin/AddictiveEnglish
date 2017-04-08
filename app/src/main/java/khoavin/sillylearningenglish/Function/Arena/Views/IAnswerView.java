package khoavin.sillylearningenglish.Function.Arena.Views;

import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.SingleViewObject.Common;

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

    void MoveToBattleResult();

    void InformTrueAnswer();

    void InformFalseAnswer();

    void InformError(SillyError error);

}
