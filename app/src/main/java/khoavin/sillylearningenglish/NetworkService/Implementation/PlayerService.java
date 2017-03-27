package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import com.android.volley.NetworkError;

import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PlayerService implements IPlayerService {

    //The current user
    private User currentUser = null;
    private String USER_SERVICE_TAG = "USER_SERVICE_TAG";

    @Override
    public void GetuserInformation(String user_id, final IServerResponse receiver) {
        IApiServices APIServices = ApiUntils.getAPIService();
        if(APIServices != null)
        {
            APIServices.getUserInformation(user_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<User>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            receiver.onError(new NetworkError(e.getCause()));
                            Log.e(USER_SERVICE_TAG, "Can not get user's information: ");
                            Log.e(USER_SERVICE_TAG, e.toString());
                        }

                        @Override
                        public void onNext(User user) {
                            if(user != null)
                            {
                                currentUser = user;
                                receiver.onSuccess(user);
                            }
                            else
                            {
                                receiver.onError(new NetworkError());
                            }

                            Log.e(USER_SERVICE_TAG, "Get user's information successfully: ");
                        }
                    });
        }
    }

    @Override
    public void GetBattleChains(String user_id, IServerResponse<User> receiver) {

    }
}
