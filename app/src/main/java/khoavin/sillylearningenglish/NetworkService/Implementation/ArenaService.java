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

import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyResponse;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.AnswerChecker;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleHistoryInfo;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ChainInfo;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.MyAnswer;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Question;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Ranking;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_ACCEPT;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_CANCEL;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_CHOSE_ANSWER;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_CREATE;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_FIND;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_GET_CHAINS;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_GET_ENEMY_DUEL;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_GET_HISTORY;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_RESULT;

public class ArenaService implements IArenaService {

    /**
     * The current enemy
     */
    private Enemy _enemy;

    /**
     * The current question list
     */
    private Question[] _questions;

    /**
     * The history information.
     */
    private BattleHistoryInfo[] _historyInfor;

    /**
     * Called battle from.
     */
    private Common.BattleCalledFrom _calledBattleFrom = Common.BattleCalledFrom.NOT_FOUND;

    /**
     * Set the value that indicate battle called from.
     *
     * @param calledFrom The view that called battle prepare.
     */
    @Override
    public void SetBattleCalledFrom(Common.BattleCalledFrom calledFrom) {
        _calledBattleFrom = calledFrom;
    }

    /**
     * Get the value that indicate battle called from.
     *
     * @return The view that called current battle.
     */
    @Override
    public Common.BattleCalledFrom CalledBattleFrom() {
        return _calledBattleFrom;
    }

    /**
     * Get the current enemy
     *
     * @return Current enemy
     */
    @Override
    public Enemy GetCurrentEnemy() {
        return _enemy;
    }

    /**
     * Get the current question list
     *
     * @return The current question list
     */
    @Override
    public Question[] GetCurrentQuestions() {
        return _questions;
    }

    /**
     * Sent create battle request to server
     *
     * @param user_id   The user Identifier
     * @param enemy_id  The enemy identifier
     * @param bet_value The bet value
     * @param message   The message
     */
    @Override
    public void CreateBattle(final String user_id, final String enemy_id, final long bet_value, final String message, final Context context, final IVolleyService volleyService, final IVolleyResponse<Question[]> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_CREATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Question[] questions = JsonConvert.getArray(response, Question[].class);
                                    if (questions != null && questions.length > 0) {
                                        _questions = questions;
                                        receiver.onSuccess(_questions);
                                    } else {
                                        _questions = null;
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    _questions = null;
                                    Common.LogError("Can not parse response as Question list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("attacker_id", user_id);
                        params.put("defender_id", enemy_id);
                        params.put("bet_value", String.valueOf(bet_value));
                        params.put("message", message);
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
     * Sent find enemy request to server
     *
     * @param user_id The player Identifier
     */
    @Override
    public void FindBattle(final String user_id, final String current_enemy_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<Enemy> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_FIND,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Enemy[] enemy = JsonConvert.getArray(response, Enemy[].class);
                                    if (enemy != null && enemy.length > 0) {
                                        _enemy = enemy[0];
                                        receiver.onSuccess(_enemy);
                                    } else {
                                        _enemy = null;
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    _enemy = null;
                                    Common.LogError("Can not parse response as Enemy list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("current_enemy_id", current_enemy_id);
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
     * Sent accept battle request to server
     *
     * @param defender_id The defender Identifier (current player)
     * @param battle_id   The battle Identifier
     */
    @Override
    public void AcceptBattle(final String defender_id, final int battle_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<Question[]> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_ACCEPT,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Question[] questions = JsonConvert.getArray(response, Question[].class);
                                    if (questions != null && questions.length > 0) {
                                        _questions = questions;
                                        receiver.onSuccess(_questions);
                                    } else {
                                        _questions = null;
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    _questions = null;
                                    Common.LogError("Can not parse response as Question list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("defender_id", defender_id);
                        params.put("battle_id", String.valueOf(battle_id));
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
     * Sent chose answer request to server
     *
     * @param user_id      The user Idenfitier
     * @param battle_id    The battle identifier
     * @param question_id  The question identifier
     * @param chose_answer The chose answer
     */
    @Override
    public void ChoseAnswer(final String user_id, final int battle_id, final int question_id, final int chose_answer, final Context context, final IVolleyService volleyService, final IVolleyResponse<AnswerChecker> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_CHOSE_ANSWER,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    AnswerChecker[] checker = JsonConvert.getArray(response, AnswerChecker[].class);
                                    if (checker != null && checker.length > 0) {
                                        receiver.onSuccess(checker[0]);
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as AnswerChecker list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("battle_id", String.valueOf(battle_id));
                        params.put("question_id", String.valueOf(question_id));
                        params.put("chose_answer", String.valueOf(chose_answer));
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
     * Get battle result
     *
     * @param user_id   The current player identifier
     * @param battle_id The battle identifier
     */
    @Override
    public void GetBattleResult(final String user_id, final int battle_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<MyAnswer[]> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_RESULT,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    MyAnswer[] myAnswers = JsonConvert.getArray(response, MyAnswer[].class);
                                    if (myAnswers != null && myAnswers.length > 0) {
                                        receiver.onSuccess(myAnswers);
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as MyAnswer list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("battle_id", String.valueOf(battle_id));
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
     * Cancel battle
     */
    @Override
    public void CancelBattle(final String user_id, final int battle_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ErrorCode> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_CANCEL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ErrorCode[] errorCodes = JsonConvert.getArray(response, ErrorCode[].class);
                                    if (errorCodes != null && errorCodes.length > 0) {
                                        receiver.onSuccess(errorCodes[0]);
                                    } else {
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as cancel battle error code");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("battle_id", String.valueOf(battle_id));
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
     * Get the duel enemy
     *
     * @param user_id   The user's Identifier
     * @param battle_id The Battle Identifier
     */
    @Override
    public void GetEnemyDuel(final String user_id, final int battle_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<Enemy> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_GET_ENEMY_DUEL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Enemy[] enemies = JsonConvert.getArray(response, Enemy[].class);
                                    if (enemies != null && enemies.length > 0) {
                                        _enemy = enemies[0];
                                        receiver.onSuccess(_enemy);
                                    } else {
                                        _enemy = null;
                                        receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                    }
                                } catch (JsonParseException ex) {
                                    _enemy = null;
                                    Common.LogError("Can not parse response as Enemy list");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("battle_id", String.valueOf(battle_id));
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
    public void GetEnemyInformationFromRankingItem(Ranking rankingItem) {
        this._enemy = new Enemy();
        this._enemy.FromRankingItem(rankingItem);
    }

    @Override
    public void GetBattleHistory(final String user_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<BattleHistoryInfo[]> receiver) {
        if (_historyInfor != null) {
            receiver.onSuccess(_historyInfor);
        } else {
            ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
                @Override
                public void onDoing() {
                    RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_GET_HISTORY,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        BattleHistoryInfo[] infos = JsonConvert.getArray(response, BattleHistoryInfo[].class);
                                        if (infos != null && infos.length > 0) {
                                            _historyInfor = infos;
                                            receiver.onSuccess(_historyInfor);
                                        } else {
                                            _historyInfor = null;
                                            receiver.onError(Common.getResponseNullOrZeroSizeErrorCode());
                                        }
                                    } catch (JsonParseException ex) {
                                        _enemy = null;
                                        Common.LogError("Can not parse response as Battle histories information list.");
                                        Common.LogError(ex.toString());
                                        try {
                                            ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                            if (error != null && error.length > 0)
                                                receiver.onError(error[0]);
                                            else
                                                receiver.onError(Common.getNotFoundErrorCode());
                                        } catch (JsonParseException ex_error) {
                                            receiver.onError(Common.getParseJsonErrorCode());
                                            Common.LogError("Can not parse response as error code");
                                            Common.LogError(ex_error.toString());
                                        }
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
    }

    @Override
    public void GetBattleChains(final String user_id, final Context context, final IVolleyService volleyService, final IVolleyResponse<ChainInfo[]> receiver) {
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(context) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(context.getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_GET_CHAINS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ChainInfo[] infos = JsonConvert.getArray(response, ChainInfo[].class);
                                    receiver.onSuccess(infos);
                                } catch (JsonParseException ex) {
                                    Common.LogError("Can not parse response as Battle chains information.");
                                    Common.LogError(ex.toString());
                                    try {
                                        ErrorCode[] error = JsonConvert.getArray(response, ErrorCode[].class);
                                        if (error != null && error.length > 0)
                                            receiver.onError(error[0]);
                                        else
                                            receiver.onError(Common.getNotFoundErrorCode());
                                    } catch (JsonParseException ex_error) {
                                        receiver.onError(Common.getParseJsonErrorCode());
                                        Common.LogError("Can not parse response as error code");
                                        Common.LogError(ex_error.toString());
                                    }
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
}
