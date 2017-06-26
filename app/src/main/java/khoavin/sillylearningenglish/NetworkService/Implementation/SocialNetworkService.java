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
import khoavin.sillylearningenglish.NetworkService.EventListener.FetchNotifyListener;
import khoavin.sillylearningenglish.NetworkService.EventListener.PostNotifyListener;
import khoavin.sillylearningenglish.NetworkService.EventListener.CommentListener;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IAuthenticationService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.ISocialNetworkService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Comment;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.NetworkAsyncTask;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.COMMENT_NOTIFY;
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
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {
                ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                if (errorCodes[0].getCode() == Common.ServiceCode.POST_NOTIFICATION_SUCCESS){
                    postNotifyListener.onPostSuccess(null);
                }
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", authenticationService.getCurrentUser().getUid());
                params.put("user_location","Việt Nam");
                params.put("notify_content",notificationContent);
                return params;
            }

            @Override
            public String getAPI_URL() {
                return POST_NOTIFICATION;
            }
        };
        networkAsyncTask.execute();
    }

    @Override
    public void getHomeNotification(final FetchNotifyListener fetchNotifyListener) {
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {
                Notification[] notifications = JsonConvert.getArray(response,Notification[].class);
                fetchNotifyListener.onFetchSuccess(ArrayConvert.toArrayList(notifications));
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", authenticationService.getCurrentUser().getUid());
                params.put("limit_amount","0");
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_USER_NOTIFICATION;
            }
        };
        networkAsyncTask.execute();
    }

    @Override
    public void getProfileNotification(final FetchNotifyListener fetchNotifyListener) {
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {
                Notification[] notifications = JsonConvert.getArray(response,Notification[].class);
                fetchNotifyListener.onFetchSuccess(ArrayConvert.toArrayList(notifications));
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", authenticationService.getCurrentUser().getUid());
                params.put("limit_amount","0");
                return params;
            }

            @Override
            public String getAPI_URL() {
                return GET_USER_PROFILE;
            }
        };
        networkAsyncTask.execute();
    }

    @Override
    public void getProfile(FetchNotifyListener fetchNotifyListener) {

    }

    @Override
    public void doComment(final Comment comment, final CommentListener commentListener) {
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask(activity) {
            @Override
            public void Response(String response) {
                ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                if (errorCodes[0].getCode() == Common.ServiceCode.COMMENT_NOTIFY_SUCCESS){
                    commentListener.commentSuccess();
                }
            }

            @Override
            public Map<String, String> getListParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", authenticationService.getCurrentUser().getUid());
                params.put("notify_id",comment.getNotifyId());
                params.put("comment_content",comment.getCommentContent());
                return params;
            }

            @Override
            public String getAPI_URL() {
                return COMMENT_NOTIFY;
            }
        };
        networkAsyncTask.execute();
    }

    @Override
    public void doLike(Notification notification) {

    }
}
