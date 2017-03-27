package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import com.android.volley.NetworkError;

import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inboxs;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InboxService implements IInboxService {
   private static final String INBOX_TAG = "INBOX SERVICE: ";

    @Override
    public void GetInboxItems(String user_id, final IServerResponse<Inboxs> receiver) {

        IApiServices Apiservice = ApiUntils.getAPIService();
        if(Apiservice != null)
        {
            Apiservice.getInboxItems(user_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Inboxs>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            receiver.onError(new NetworkError(e));
                            Log.e(INBOX_TAG, "Can not get inbox items!");
                            Log.e(INBOX_TAG, e.toString());
                        }

                        @Override
                        public void onNext(Inboxs inboxs) {
                            receiver.onSuccess(inboxs);
                            Log.i(INBOX_TAG, "Get inbox items successfully!");
                        }
                    });
        }

    }
}
