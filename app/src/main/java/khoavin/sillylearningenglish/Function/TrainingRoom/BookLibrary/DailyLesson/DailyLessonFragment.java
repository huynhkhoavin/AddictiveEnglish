package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.DailyLesson;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Storage.UserStorageFragment;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_DAILY_LESSON;

/**
 * Created by KhoaVin on 03/06/2017.
 */

public class DailyLessonFragment extends UserStorageFragment {
    @Override
    public void getUserLesson() {
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(getActivity()) {
            @Override
            public void Response(String response) {
                Lesson[] lessons = JsonConvert.getArray(response,Lesson[].class);
                singleViewAdapter.setDataSource(ArrayConvert.toObjectArray(lessons));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("user_id",authenticationService.getCurrentUser().getUid());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_DAILY_LESSON;
            }
        };
        networkAsyncTask.execute();
    }
}
