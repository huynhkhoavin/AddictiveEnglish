package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;

/**
 * Created by KhoaVin on 2/28/2017.
 */

public interface IFirebaseUserService {
    void checkOnlineStatus(FirebaseAccount[] firebaseAccounts);
}
