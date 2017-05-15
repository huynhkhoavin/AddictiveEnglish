package khoavin.sillylearningenglish.NetworkService.Implementation;

import khoavin.sillylearningenglish.NetworkService.Interfaces.ILeaderboardService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Leaderboards;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ErrorConverter;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LeaderboardService implements ILeaderboardService {

    private static final String LEADER_BOARD_TAG = "LEADER BOARD SERVICE: ";

    @Override
    public void GetGlobalRanking(String user_id, final IServerResponse<Leaderboards> receiver) {
        IApiServices APISrevice = ApiUntils.getAPIService();
        if(APISrevice != null)
        {
            APISrevice.getGlobalRanking(user_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Leaderboards>() {
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
                        public void onNext(Leaderboards leaderboards) {
                            receiver.onSuccess(leaderboards);
                        }
                    });
        }
    }

    @Override
    public void GetFriendRanking(String user_id, final IServerResponse<Leaderboards> receiver) {
        IApiServices APIService = ApiUntils.getAPIService();
        if(APIService != null)
        {
            APIService.getFriendRanking(user_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Leaderboards>() {
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
                        public void onNext(Leaderboards leaderboards) {
                            receiver.onSuccess(leaderboards);
                        }
                    });
        }
    }
}
