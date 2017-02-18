package khoavin.sillylearningenglish.FUNCTION.Arena.Models.Implementation;

import khoavin.sillylearningenglish.FUNCTION.Arena.Models.IRankingModel;
import khoavin.sillylearningenglish.SINGLE_OBJECT.*;
import khoavin.sillylearningenglish.TEST_DATA.TestData;

/**
 * Created by OatOal on 2/18/2017.
 */

public class RankingModel implements IRankingModel {

    //region Properties

    //The user's information
    private User user;

    //The ranking list
    //........................//

    //endregion

    @Override
    public User getUserInformation() {

        return TestData.getInstance().getUser();
    }

    //Ranking list implement
}
