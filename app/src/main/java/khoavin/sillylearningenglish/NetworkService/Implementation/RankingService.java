package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import khoavin.sillylearningenglish.NetworkService.Interfaces.IRankingService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Ranking;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.REQUEST_ADD_FRIEND;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.RANKING_GET_FRIEND_RANKING;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.RANKING_GET_GLOBAL_RANKING;

/**
 * Created by OatOal on 5/24/2017.
 */

public class RankingService implements IRankingService {

    /**
     * Storage the friend ranking.
     */
    private ArrayList<Ranking> _friendRanking;

    /**
     * Storage the global ranking.
     */
    private ArrayList<Ranking> _globalRanking;

    /**
     * Gets the friend ranking.
     * @param userId The user's identifier.
     */
    @Override
    public void GetFriendRanking(final String userId, final Context context, final IVolleyService volleyService, final IVolleyResponse<ArrayList<Ranking>> volleyResponse) {
        if(_friendRanking != null)
        {
            volleyResponse.onSuccess(_friendRanking);
        }
        else
        {
            ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
                @Override
                public void onDoing() {
                    RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, RANKING_GET_FRIEND_RANKING,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Ranking[] rankings = JsonConvert.getArray(response, Ranking[].class);
                                        if (rankings != null) {
                                            _friendRanking = ArrayConvert.toArrayList(rankings);
                                            DoSomethingWithRankingList(true);
                                            volleyResponse.onSuccess(_friendRanking);
                                        } else {
                                            volleyResponse.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                        }
                                    } catch (JsonParseException ex) {
                                        Common.LogError("Can not parse response as friend ranking array list.");
                                        Common.LogError(ex.toString());
                                        try {
                                            ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                            if (error != null && error.length > 0)
                                                volleyResponse.onError(error[0]);
                                            else
                                                volleyResponse.onError(Common.getNotFoundErrorCode());
                                        } catch (JsonParseException ex_error) {
                                            volleyResponse.onError(Common.getParseJsonErrorCode());
                                            Common.LogError("Can not parse response as error code");
                                            Common.LogError(ex_error.toString());
                                        }
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error");
                            volleyResponse.onError(Common.getInternalServerErrorCode(error));
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", userId);
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
    }

    /**
     * Gets the global ranking.
     * @param userId The user's identifier.
     */
    @Override
    public void GetGlobalRanking(final String userId, final Context context, final IVolleyService volleyService, final IVolleyResponse<ArrayList<Ranking>> volleyResponse) {
        if(_globalRanking != null)
        {
            volleyResponse.onSuccess(_globalRanking);
        }
        else
        {
            ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
                @Override
                public void onDoing() {
                    RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, RANKING_GET_GLOBAL_RANKING,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Ranking[] rankings = JsonConvert.getArray(response, Ranking[].class);
                                        if (rankings != null) {
                                            _globalRanking = ArrayConvert.toArrayList(rankings);
                                            DoSomethingWithRankingList(false);
                                            volleyResponse.onSuccess(_globalRanking);
                                        } else {
                                            volleyResponse.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                        }
                                    } catch (JsonParseException ex) {
                                        Common.LogError("Can not parse response as global ranking array list.");
                                        Common.LogError(ex.toString());
                                        try {
                                            ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                            if (error != null && error.length > 0)
                                                volleyResponse.onError(error[0]);
                                            else
                                                volleyResponse.onError(Common.getNotFoundErrorCode());
                                        } catch (JsonParseException ex_error) {
                                            volleyResponse.onError(Common.getParseJsonErrorCode());
                                            Common.LogError("Can not parse response as error code");
                                            Common.LogError(ex_error.toString());
                                        }
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error");
                            volleyResponse.onError(Common.getInternalServerErrorCode(error));
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_id", userId);
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
    }

    /**
     * Add current selected user to current user friend list.
     * @param user_id The current user identifier.
     * @param friend_id The user identifier will be add to current user friend list.
     */
    @Override
    public void AddFriend(final String user_id, final String friend_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> volleyResponse) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REQUEST_ADD_FRIEND,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] rankings = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (rankings != null && rankings.length > 0) {
                                        volleyResponse.onSuccess(rankings[0]);
                                    } else {
                                        volleyResponse.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as add friend response.");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            volleyResponse.onError(error[0]);
                                        else
                                            volleyResponse.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        volleyResponse.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error");
                        volleyResponse.onError(Common.getInternalServerErrorCode(error));
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("friend_id", friend_id);
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

    /**
     * sort ranking list.
     */
    private void DoSomethingWithRankingList(boolean isFriendRanking)
    {
        if(isFriendRanking)
        {
            if(_friendRanking != null && _friendRanking.size() > 0)
            {
                for(int i = 0; i < _friendRanking.size(); i++)
                {
                    _friendRanking.get(i).setRank(i + 1);
                }
            }
        }
        else
        {
            //Set rank
            if(_globalRanking != null && _globalRanking.size() > 0)
            {
                for(int i = 0; i < _globalRanking.size(); i++)
                {
                    _globalRanking.get(i).setRank(i + 1);
                }
            }

        }
    }
}
