package khoavin.sillylearningenglish.FUNCTION.Arena.Models.Implementation;

import khoavin.sillylearningenglish.FUNCTION.Arena.Models.IAnswerModel;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Question;
import khoavin.sillylearningenglish.TEST_DATA.TestData;

/**
 * Created by OatOal on 2/18/2017.
 */

public class AnswerModel implements IAnswerModel {

    @Override
    public Question[] GetQuestionList() {
        return TestData.getInstance().getQuestionList();
    }
}
