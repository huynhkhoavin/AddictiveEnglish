package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import rx.functions.Func1;

public interface IUserService {

    //Get user infrmation
    void GetuserInformation(String user_id, final Func1<User, Void> receiver);

    //Get current user
    User GetCurrentUser();

}
