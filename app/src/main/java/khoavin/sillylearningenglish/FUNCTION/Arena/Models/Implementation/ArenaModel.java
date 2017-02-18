package khoavin.sillylearningenglish.FUNCTION.Arena.Models.Implementation;

import khoavin.sillylearningenglish.FUNCTION.Arena.Models.IAreanaModel;
import khoavin.sillylearningenglish.SINGLE_OBJECT.User;
import khoavin.sillylearningenglish.TEST_DATA.TestData;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ArenaModel implements IAreanaModel {

//region Properties

    //The user's information
    private User user;

    //endregion

    @Override
    public User getUserInformation() {

        return TestData.getInstance().getUser();
    }

}
