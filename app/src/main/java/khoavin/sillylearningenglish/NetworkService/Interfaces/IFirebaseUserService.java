package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.FirebaseObject.UserAccount;

/**
 * Created by KhoaVin on 2/28/2017.
 */

public interface IFirebaseUserService {
    boolean checkOnlineStatus(UserAccount userAccount);
}
