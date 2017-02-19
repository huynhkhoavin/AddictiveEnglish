package khoavin.sillylearningenglish.SERVICE.Implementation;

import khoavin.sillylearningenglish.SERVICE.APIUntils;
import khoavin.sillylearningenglish.SERVICE.IAPIServices;
import khoavin.sillylearningenglish.SERVICE.Interfaces.IUserService;
import khoavin.sillylearningenglish.SERVICE.WebModels.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by OatOal on 2/19/2017.
 */

public class UserService implements IUserService {

    //The current user
    private User currentUser = null;

    @Override
    public void GetuserInformation(String userId, final Func2<User, Exception, Void> receiver) {
        IAPIServices APIServices = APIUntils.getAPIService();
        if(APIServices != null)
        {
            APIServices.getUserInformation(userId, receiver)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<User>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            //Call receiver
                            receiver.call(null, new Exception(e.getMessage(), e.getCause()));
                        }

                        @Override
                        public void onNext(User user) {

                            //Set current user value; Call receiver
                            currentUser = user;
                            receiver.call(user, null);
                        }
                    });
        }
    }

    @Override
    public User GetCurrentUser() {
        return currentUser;
    }
}
