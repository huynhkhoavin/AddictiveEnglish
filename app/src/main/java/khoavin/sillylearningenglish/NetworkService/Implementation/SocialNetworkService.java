package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Social.Event.FetchNotifyListener;
import khoavin.sillylearningenglish.Function.Social.Event.PostNotifyListener;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ISocialNetworkService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_USER_NOTIFICATION;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.GET_USER_PROFILE;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.POST_NOTIFICATION;

/**
 * Created by Dev02 on 5/31/2017.
 */

public class SocialNetworkService implements ISocialNetworkService {

    Activity activity;
    @Inject
    IVolleyService volleyService;
    @Inject
    IAuthenticationService authenticationService;
    @Override
    public void Init(Activity activity) {
        this.activity = activity;
        ((SillyApp) activity.getApplication()).getDependencyComponent().inject(this);
    }

    @Override
    public void postNotification(final String notificationContent, final PostNotifyListener postNotifyListener) {
        ProgressAsyncTask progressThreadTask = new ProgressAsyncTask(activity) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(activity);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_NOTIFICATION,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                                if (errorCodes[0].getCode() == Common.ServiceCode.POST_NOTIFICATION_SUCCESS){
                                    postNotifyListener.onPostSuccess(null);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", authenticationService.getCurrentUser().getUid());
                        params.put("user_location","Việt Nam");
                        params.put("notify_content",notificationContent);
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

    @Override
    public void getHomeNotification(final FetchNotifyListener fetchNotifyListener) {
        ProgressAsyncTask progressThreadTask = new ProgressAsyncTask(activity) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(activity);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USER_NOTIFICATION,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Notification[] notifications = JsonConvert.getArray(response,Notification[].class);
                                fetchNotifyListener.onFetchSuccess(ArrayConvert.toArrayList(notifications));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        fetchNotifyListener.onFetchFailed(error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", authenticationService.getCurrentUser().getUid());
                        params.put("limit_amount","0");
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

    @Override
    public void getProfileNotification(final FetchNotifyListener fetchNotifyListener) {
        ProgressAsyncTask progressThreadTask = new ProgressAsyncTask(activity) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(activity);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USER_PROFILE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Notification[] notifications = JsonConvert.getArray(response,Notification[].class);
                                fetchNotifyListener.onFetchSuccess(ArrayConvert.toArrayList(notifications));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        fetchNotifyListener.onFetchFailed(error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", authenticationService.getCurrentUser().getUid());
                        params.put("limit_amount","0");
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

    @Override
    public void getProfile(FetchNotifyListener fetchNotifyListener) {

    }
}
