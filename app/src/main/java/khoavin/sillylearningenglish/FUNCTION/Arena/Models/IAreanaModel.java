package khoavin.sillylearningenglish.FUNCTION.Arena.Models;

import khoavin.sillylearningenglish.SERVICE.WebModels.User;
import rx.functions.Func2;

/**
 * Created by OatOal on 2/18/2017.
 */

public interface IAreanaModel {

    //Gets the user's information
    void getUserInformation(String userId, Func2<User, Exception, Void> onCompleted);
}
