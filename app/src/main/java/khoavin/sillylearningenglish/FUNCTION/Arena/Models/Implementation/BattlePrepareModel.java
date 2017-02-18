package khoavin.sillylearningenglish.FUNCTION.Arena.Models.Implementation;

import khoavin.sillylearningenglish.FUNCTION.Arena.Models.IBattlePrepareModel;
import khoavin.sillylearningenglish.SINGLE_OBJECT.User;
import khoavin.sillylearningenglish.TEST_DATA.TestData;

/**
 * Created by OatOal on 2/18/2017.
 */

public class BattlePrepareModel implements IBattlePrepareModel {


    @Override
    public User GetUserAInformation() {
        return TestData.getInstance().getUser();
    }

    @Override
    public User GetUserBInformation() {
        return TestData.getInstance().getUser();
    }
}
