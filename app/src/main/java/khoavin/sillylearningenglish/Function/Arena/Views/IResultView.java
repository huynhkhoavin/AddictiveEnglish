package khoavin.sillylearningenglish.Function.Arena.Views;

import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IResultView {

    void HighlineTrueAnswer(Common.AnswerKey answerKey);

    void HideOrShowHearIcon(boolean show);

    void SetAnswerA(String answerA);

    void SetAnswerB(String answerB);

    void SetQuestionContent(String questionContent);

    void SetQuestionTitle(String questionTitle);

    void SetAnswerArrayButton(boolean[] answerState);

    void SetTotalTimes(long milisecond);
}
