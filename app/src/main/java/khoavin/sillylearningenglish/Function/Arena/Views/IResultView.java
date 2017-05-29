package khoavin.sillylearningenglish.Function.Arena.Views;

import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IResultView {

    void HeightLineTrueAnswer(Common.AnswerKey answerKey);

    void setQuestionType(Common.QuestionType questionType);

    void SetAnswerA(String answerA);

    void SetAnswerB(String answerB);

    void SetQuestionContent(String questionContent);

    void SetQuestionTitle(String questionTitle);

    void SetAnswerArrayButton(boolean[] answerState);

    void SetTotalTimes(long milisecond);

    void SetButtonState(Common.BattleCalledFrom calledFrom);

    void HighlighSelectedAnswer(int index);
}
