package khoavin.sillylearningenglish.FUNCTION.Arena.Models.Implementation;

import khoavin.sillylearningenglish.FUNCTION.Arena.Models.IResultModel;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Result;
import khoavin.sillylearningenglish.TEST_DATA.TestData;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ResultModel implements IResultModel {

    @Override
    public Result GetUserAnswer() {

        return TestData.getInstance().getResult();
    }
}
