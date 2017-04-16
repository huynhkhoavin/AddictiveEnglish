package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import com.android.volley.NetworkError;

import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ErrorConverter;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
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
                            ErrorConverter eConverter = ApiUntils.getErrorConverter();
                            if(eConverter != null)
                                receiver.onError(eConverter.ConvertThrowable(e));
                            else
                                receiver.onError(ErrorConverter.NotInitializeErrorConverter());
                        }

                        @Override
                        public void onNext(User user) {
                            currentUser = user;
                            receiver.onSuccess(user);
                        }
                    });
        }
    }

    @Override
    public void GetBattleChains(String user_id, IServerResponse<User> receiver) {

    }

    @Override
    public User GetCurrentUser() {
        return this.currentUser;
    }

    private  void DoOnSub()
    {

    }
}
