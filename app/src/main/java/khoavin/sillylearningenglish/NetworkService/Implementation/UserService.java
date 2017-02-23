package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import com.android.volley.NetworkError;

import khoavin.sillylearningenglish.NetworkService.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IUserService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserService implements IUserService {

    //The current user
    private User currentUser = null;
    private String USER_SERVICE_TAG = "USER_SERVICE_TAG";

    @Override
    public void GetuserInformation(final IServerResponse<User> serverResponse) {
        IApiServices APIServices = ApiUntils.getAPIService();
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
                            serverResponse.onError(new NetworkError(e));
                            Log.e(USER_SERVICE_TAG, "Can not get user's information: ");
                            Log.e(USER_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(User user) {
                            currentUser = user;
                            serverResponse.onSuccess(user);
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
