package khoavin.sillylearningenglish.NetworkService.Implementation;

import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lessons;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ErrorConverter;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Khoavin on 4/2/2017.
 */

public class TrainingService implements ITrainingService {
    @Override
    public void GetPopularLesson(final IServerResponse<Lessons> receiver){
        IApiServices APIServices = ApiUntils.getAPIService();
        if(APIServices != null)
        {
            APIServices.getPopularLesson()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Lessons>() {
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
                        public void onNext(Lessons lessons) {
                            receiver.onSuccess(lessons);
                        }
                    });
        }
    }
}
