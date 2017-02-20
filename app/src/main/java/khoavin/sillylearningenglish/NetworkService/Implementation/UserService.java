package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import khoavin.sillylearningenglish.NetworkService.APIUntils;
import khoavin.sillylearningenglish.NetworkService.IAPIServices;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IUserService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class UserService implements IUserService {

    //The current user
    private User currentUser = null;
    private String USER_SERVICE_TAG = "USER_SERVICE_TAG";

    @Override
    public void GetuserInformation(final Func1<User, Void> receiver) {
        IAPIServices APIServices = APIUntils.getAPIService();
        if(APIServices != null)
        {
            APIServices.getUserInformation("haha")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<User>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            receiver.call(null);
                            Log.e(USER_SERVICE_TAG, "Can not get user's information: ");
                            Log.e(USER_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(User user) {
                            currentUser = user;
                            receiver.call(user);
                            Log.e(USER_SERVICE_TAG, "Get user's information successfully: ");
                        }
                    });
        }
    }

    @Override
    public User GetCurrentUser() {
        return currentUser;
    }
}
