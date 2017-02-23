package khoavin.sillylearningenglish.NetworkService.Implementation;

public class UserService implements IUserService {

    //The current user
    private User currentUser = null;
    private String USER_SERVICE_TAG = "USER_SERVICE_TAG";

    @Override
    public void GetuserInformation(final IServerResponse<User> serverResponse) {
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
