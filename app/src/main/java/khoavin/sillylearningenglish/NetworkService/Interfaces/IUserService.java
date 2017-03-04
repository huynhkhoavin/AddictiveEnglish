package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.functions.Func1;

public interface IUserService {

    //Get user infrmation
    void GetuserInformation(String user_id, IServerResponse<User> receiver);

    //Get current user
    User GetCurrentUser();

}
