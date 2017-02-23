package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.NetworkService.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;

public interface IUserService {

    //Get user infrmation
    void GetuserInformation(final IServerResponse<User> callBack);

    //Get current user
    User GetCurrentUser();

}
