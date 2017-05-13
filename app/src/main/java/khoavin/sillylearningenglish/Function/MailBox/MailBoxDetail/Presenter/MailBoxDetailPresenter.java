package khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.Presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonParseException;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.Arena.Views.Implementation.AnswerActivity;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxDetail.View.IMailBoxDetailView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IArenaService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Enemy;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Questions;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.BATTLE_GET_ENEMY_DUEL;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.CHECK_LESSON_WAS_BOUGHT;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_CLAIM;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_DELETE;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_RATE;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxDetailPresenter implements IMailBoxDetailPresenter
{
    //region properties

    /**
     * Inject the player service
     */
    @Inject
    IPlayerService playerService;

    /**
     * Inject the arena service
     */
    @Inject
    IArenaService arenaService;

    /**
     * Inject volley service
     */
    @Inject
    IVolleyService volleyService;

    /**
     * Presenter for view
     */
    private IMailBoxDetailView theView;

    /**
     * The datacontext
     */
    private Inbox dataContext;

    /**
     * The duel enemy
     */
    private Enemy duelEnemy;

    //endregion

    //region Implementation

    @Override
    public void SetDataContext(Inbox data)
    {
        this.dataContext = data;
        Date d = null;
        try
        {
            d = dataContext.getDateCreate();
            theView.SetTime(d);
        } catch (ParseException e)
        {
            d = new Date(2017, 1, 1, 12, 0, 0);
            theView.SetTime(d);
            e.printStackTrace();
        }


        theView.SetMailType(dataContext.getMailType());
        switch (dataContext.getMailType()) {
            case BATTLE_CHALLENGE:
                theView.SetTitle(GetString(R.string.mail_title_battle_challenge));
                theView.SetStatus(GetString(R.string.mail_status_information));
                theView.SetMessage(
                        String.format(GetString(R.string.mail_content_battle_challenge),
                                dataContext.getSenderName(),
                                Common.FormatBigNumber(data.getValue()), data.getContent()));
                break;
            case BATTLE_RESULT:
                theView.SetTitle(GetString(R.string.mail_title_battle_report));
                theView.SetStatus(GetString(R.string.mail_status_battle_won));
                theView.SetCoins(dataContext.getValue());
                theView.SetUpDownRank("BẠC I");
                break;
            case GIFT_COIN:
                theView.SetCoins(dataContext.getValue());
                break;
            case SYSTEM_MESSAGE:
                break;
            case NOT_FOUND:
                break;
        }
    }

    @Override
    public void RattingMail() {
        //If current rated -> will unrate
        //If current not rated -> will rate

        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(GetView()) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(GetView().getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_RATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    //Dosomething to parse response to model
                                }
                                catch (JsonParseException ex)
                                {
                                    try {
                                        ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                                    }
                                    catch (JsonParseException ex_error)
                                    {
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("user_id", playerService.GetCurrentUser().getUserId());
                        params.put("mail_id", dataContext.getId().toString());
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
    public void DeleteMail() {
        //Do something to sent delete mail request to server
        //Do something to back to mailbox and remove item from mailbox

        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(GetView()) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(GetView().getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_DELETE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    //Dosomething to parse response to model
                                }
                                catch (JsonParseException ex)
                                {
                                    try {
                                        ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                                    }
                                    catch (JsonParseException ex_error)
                                    {
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("user_id", playerService.GetCurrentUser().getUserId());
                        params.put("mail_id", dataContext.getId().toString());
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
    public void BackToInbox() {
        //Do something to back to mailbox
    }

    @Override
    public void AcceptBattle() {
        if(duelEnemy != null)
        {
            //Move to battle prepare
        }
        else
        {
            ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(GetView()) {
                @Override
                public void onDoing() {
                    RequestQueue queue = volleyService.getRequestQueue(GetView().getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, BATTLE_GET_ENEMY_DUEL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                {
                                    try
                                    {
                                        //Dosomething to parse response to model
                                        Enemy[] items = JsonConvert.getArray(response,Enemy[].class);
                                        if(items != null && items.length > 0)
                                        {
                                            duelEnemy = items[0];
                                            //Move to battle prepare
                                        }
                                    }
                                    catch (JsonParseException ex)
                                    {
                                        try {
                                            ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                                            Common.LogError(errorCodes[0].toString());
                                        }
                                        catch (JsonParseException ex_error)
                                        {
                                            Common.LogError(ex_error.toString());
                                        }
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
                            params.put("user_id", playerService.GetCurrentUser().getUserId());
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
//        arenaService.AcceptBattle(playerService.GetCurrentUser().getUserId(), Integer.valueOf(dataContext.getValue()), new IServerResponse<Questions>() {
//            @Override
//            public void onSuccess(Questions questions) {
//                Intent it = new Intent((AppCompatActivity)theView, AnswerActivity.class);
//                it.putExtra("BATTLE_FROM", Common.BattleFrom.MAIL_DETAIL);
//                ((AppCompatActivity)theView).startActivity(it);
//            }
//
//            @Override
//            public void onError(SillyError sillyError) {
//
//            }
//        });
    }

    @Override
    public void CancelBattle() {

    }

    @Override
    public void ClaimReward() {
        //Sent claim reward request to server
        ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(GetView()) {
            @Override
            public void onDoing() {
                RequestQueue queue = volleyService.getRequestQueue(GetView().getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_CLAIM,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                try
                                {
                                    //Dosomething to parse response to model
                                }
                                catch (JsonParseException ex)
                                {
                                    try {
                                        ErrorCode[] errorCodes = JsonConvert.getArray(response,ErrorCode[].class);
                                    }
                                    catch (JsonParseException ex_error)
                                    {
                                        Common.LogError(ex_error.toString());
                                    }
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
                        params.put("user_id", playerService.GetCurrentUser().getUserId());
                        params.put("mail_id", dataContext.getId().toString());
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
    //endregion

    /**
     * Initialize the mail box detail presenter
     * @param theView
     * presenter for view
     */
    public MailBoxDetailPresenter(IMailBoxDetailView theView)
    {
        //Initialize the view
        this.theView = theView;

        //Reset value of duel enemy
        this.duelEnemy = null;

        //Inject dependency
        ((SillyApp) GetView().getApplication())
                .getDependencyComponent()
                .inject(this);
    }

    /**
     * Get string from config file
     * @param id
     * The string indentifier
     * @return
     * The string result
     */
    private String GetString(int id)
    {
        return GetView().getResources().getString(id);
    }

    /**
     * Get the view
     * @return
     * The appcompat
     */
    private  AppCompatActivity GetView()
    {
        return (AppCompatActivity) theView;
    }

    /**
     * Move to battle prepare
     * @param duelEnemy
     * The duel enemy
     */
    private void MoveToBattlePrepare(Enemy duelEnemy)
    {
        
    }
}
