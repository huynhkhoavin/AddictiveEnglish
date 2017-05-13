package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.Presenter;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View.IMailBoxView;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IInboxService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IPlayerService;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.ErrorCode;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inboxs;
import khoavin.sillylearningenglish.NetworkService.Retrofit.IServerResponse;
import khoavin.sillylearningenglish.NetworkService.Retrofit.SillyError;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.JsonConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_GET_ITEMS;
import static khoavin.sillylearningenglish.SYSTEM.Constant.WebAddress.MAIL_RATE;

/**
 * Created by KhoaVin on 2/17/2017.
 */

public class MailBoxPresenter implements IMailBoxPresenter {
    private IMailBoxView inboxView;
    private AppCompatActivity GetView(){return (AppCompatActivity)inboxView;}

    /**
     * Inject the player service
     */
    @Inject
    IPlayerService playerService;

    /**
     * Inject the volley service
     */
    @Inject
    IVolleyService volleyService;

    /**
     * Initialize the MailBoxPresenter
     * @param inboxView
     */
    public MailBoxPresenter(IMailBoxView inboxView){
        this.inboxView = inboxView;

        //inject arena service
        ((SillyApp) ((AppCompatActivity) inboxView).getApplication())
                .getDependencyComponent()
                .inject(this);

        //Initialize inbox view
        InitializeInBoxView();
    }

    /**
     * Initialize inbox view
     * - Get inbox items from service
     * - Add inbox item to ScrollView on InboxView
     * - Response error if have
     */
    private void InitializeInBoxView()
    {
        if(CheckCondition())
        {
//            inboxService.GetInboxItems(playerService.GetCurrentUser().getUserId(), new IServerResponse<Inboxs>() {
//                @Override
//                public void onSuccess(Inboxs items) {
//                    inboxView.ShowMailList(items);
//                }
//
//                @Override
//                public void onError(SillyError error) {
//
//                }
//            });

            ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(GetView()) {
                @Override
                public void onDoing() {
                    RequestQueue queue = volleyService.getRequestQueue(GetView().getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIL_GET_ITEMS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                {
                                    try
                                    {
                                        //Dosomething to parse response to model
                                        Inbox[] items = JsonConvert.getArray(response,Inbox[].class);
                                        inboxView.ShowMailList(items);
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
    }

    private boolean CheckCondition()
    {
        if(this.playerService == null || this.volleyService == null || this.playerService.GetCurrentUser() == null)
            return false;
        return true;
    }

    @Override
    public void RefreshInboxItems(String user_name) {

    }

    @Override
    public void RemoveItem(String user_id, int mail_id) {

    }

    @Override
    public void SentItem(String sender_id, String receiver_id, String message) {

    }
}
