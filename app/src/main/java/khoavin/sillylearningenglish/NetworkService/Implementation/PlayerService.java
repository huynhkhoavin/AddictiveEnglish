package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonParseException;

import java.util.HashMap;
import java.util.Map;

import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.AppParam;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.User;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.USER_GET_APP_PARAMS;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.USER_GET_OR_CREATE;


public class PlayerService implements IPlayerService {

    /**
     * Storage the current User information
     */
    private User _user = null;

    /**
     * Storage the app params.
     */
    private AppParam[] appParams = null;

    /**
     * Get the user information
     *
     * @param user_id       The user's Identifier
     * @param user_name     The user's name
     * @param user_avatar   The user's avatar
     * @param context       The Application context
     * @param volleyService The Volley service
     * @param receiver      The receiver to receive response from server
     */
    @Override
    public void GetuserInformation(final String user_id, final String user_name, final String user_avatar, final Context context, final IVolleyService volleyService, final IVolleyResponse<User> receiver) {

        //Get app params if need.
        if(this.appParams == null)
        {
            GetAppParams(user_id, context, volleyService, new IVolleyResponse<AppParam[]>() {
                @Override
                public void onSuccess(AppParam[] responseObj) {

                }

                @Override
                public void onError(ErrorCode errorCode) {

                }
            });
        }

        //Get user's informations.
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_GET_OR_CREATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                        receiver.onError(responseCodes[0]);
                                        _user = null;
                                    } else {
                                        User[] users = JsonConvert.getArray(response, User[].class);
                                        if (users != null && users.length > 0) {
                                            _user = users[0];
                                            receiver.onSuccess(_user);
                                        } else {
                                            _user = null;
                                            receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                        }
                                    }
                                } catch (JsonParseException ex) {
                                    receiver.onError(Common.getParseJsonErrorCode());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        receiver.onError(Common.getInternalServerErrorCode(error));

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("user_name", user_name);
                        params.put("user_avatar", user_avatar);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };

        progressAsyncTask.execute();
    }

    @Override
    public void GetAppParams(final String user_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<AppParam[]> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_GET_APP_PARAMS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] responseCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (responseCodes != null && responseCodes.length > 0 && !responseCodes[0].isNullInstance()) {
                                        receiver.onError(responseCodes[0]);
                                        _user = null;
                                    } else {
                                        AppParam[] params = JsonConvert.getArray(response, AppParam[].class);
                                        if (params != null && params.length > 0) {
                                            appParams = params;
                                            receiver.onSuccess(appParams);
                                        } else {
                                            appParams = null;
                                            receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                        }
                                    }
                                } catch (JsonParseException ex) {
                                    receiver.onError(Common.getParseJsonErrorCode());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        receiver.onError(Common.getInternalServerErrorCode(error));

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }

            @Override
            public void onTaskComplete(Void aVoid) {

            }
        };

        progressAsyncTask.execute();
    }
//
//    @Override
//    public void GetBattleChains(String user_id, Context context, IVolleyService volleyService, IVolleyResponse<User> receiver) {
//
//    }

    @Override
    public User GetCurrentUser() {
        return this._user;
    }

    @Override
    public AppParam GetAppParams(Common.ParamType type) {

        if(appParams != null)
        {
            for(int i = 0; i < appParams.length; i++)
            {
                if(appParams[i].getParamType() == type)
                {
                    return appParams[i];
                }
            }
        }
        return null;
    }
}
