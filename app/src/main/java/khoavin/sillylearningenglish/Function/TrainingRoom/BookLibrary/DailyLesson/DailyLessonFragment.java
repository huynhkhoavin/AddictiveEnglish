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
        ProgressAsyncTask progressThreadTask = new ProgressAsyncTask(getContext()) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_DAILY_LESSON,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Lesson[] lessons = JsonConvert.getArray(response,Lesson[].class);
                                singleViewAdapter.setDataSource(ArrayConvert.toObjectArray(lessons));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        //params.put("user_id",authenticationService.getCurrentUser().getUid());
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };
        progressThreadTask.execute();
    }
}
