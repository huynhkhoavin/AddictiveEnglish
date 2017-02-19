package khoavin.sillylearningenglish.SERVICE.Interfaces;

import khoavin.sillylearningenglish.SERVICE.WebModels.User;
import rx.functions.Func1;

public interface IUserService {

    //Get user infrmation
    void GetuserInformation(final Func1<User, Void> receiver);

    //Get current user
    User GetCurrentUser();

}
