package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.app.Activity;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lessons;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;

/**
 * Created by Khoavin on 4/2/2017.
 */

public interface ITrainingService {
    void RatingLesson(int ls_id, float rating_point, Activity activity, IVolleyResponse<ErrorCode> volleyResponse);
}
