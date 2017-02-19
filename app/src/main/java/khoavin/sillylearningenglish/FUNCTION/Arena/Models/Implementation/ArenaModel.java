package khoavin.sillylearningenglish.FUNCTION.Arena.Models.Implementation;

import khoavin.sillylearningenglish.FUNCTION.Arena.Models.IAreanaModel;
import khoavin.sillylearningenglish.SERVICE.WebModels.User;
import rx.functions.Func1;

/**
 * Created by OatOal on 2/18/2017.
 */

public class ArenaModel implements IAreanaModel {

    @Override
    public void getUserInformation(String id, Func1<User, Void> OnCompleted) {

//        IUserService userService = new UserService();
//        userService.GetuserInformation("1258HkJ13",
//                (info) ->
//                {
////                    OnCompleted.call(info);
////                    return null;
//                });
//
//        //return TestData.getInstance().getUser();
    }
}
