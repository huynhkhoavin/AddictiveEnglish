package khoavin.sillylearningenglish.FUNCTION.Arena.Models;

import khoavin.sillylearningenglish.SERVICE.WebModels.User;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IBattlePrepareModel {

    //userA information
    User GetUserAInformation();

    //UserB information
    User GetUserBInformation();

}
