package khoavin.sillylearningenglish.NetworkService.Interfaces;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseUser;

/**
 * Created by KhoaVin on 2/28/2017.
 */

public interface IFirebaseUserService {
    boolean checkOnlineStatus(FirebaseUser firebaseUser);
}
