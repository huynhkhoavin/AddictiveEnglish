package khoavin.sillylearningenglish.SERVICE.Interfaces;

import khoavin.sillylearningenglish.SERVICE.WebModels.User;
import rx.functions.Func2;

/**
 * Created by OatOal on 2/19/2017.
 */

public interface IUserService {

    //Get user infrmation
    void GetuserInformation(String userId, final Func2<User, Exception, Void> receiver);

    //Get current user
    User GetCurrentUser();

}
