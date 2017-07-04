package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import khoavin.sillylearningenglish.NetworkService.Interfaces.ITrainingService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lessons;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ApiUntils;
import khoavin.sillylearningenglish.NetworkService.Retrofit.ErrorConverter;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IApiServices;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Khoavin on 4/2/2017.
 */

public class TrainingService implements ITrainingService {
    @Override
    public void RatingLesson(final int ls_id, final float rating_point, Activity activity, final IVolleyResponse<ErrorCode> volleyResponse) {
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {
                ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                volleyResponse.onSuccess(errorCodes[0]);
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ls_id",String.valueOf(ls_id));
                params.put("rating_point",String.valueOf(rating_point));
                return params;
            }

            @Override
            public String getAPI_URL() {
                return WebAddress.RATING_LESSON;
            }
        };
        networkAsyncTask.execute();
    }
}
