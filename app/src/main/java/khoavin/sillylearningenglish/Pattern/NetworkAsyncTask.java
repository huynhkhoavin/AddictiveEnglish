package khoavin.sillylearningenglish.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import javax.inject.Inject;

import dmax.dialog.SpotsDialog;
import khoavin.sillylearningenglish.Depdency.SillyApp;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;

/**
 * Created by KhoaVin on 24/06/2017.
 */

public abstract class NetworkAsyncTask extends AsyncTask<Integer, Integer, Void>{
    private AlertDialog progressDialog;
    private Activity currentActivity;
    private YesNoDialog yesNoDialog;
    @Inject
    IVolleyService volleyService;

    public NetworkAsyncTask(Activity currentActivity, Context context) {
        this.currentActivity = currentActivity;

        progressDialog = new SpotsDialog(context);
        progressDialog.setTitle("Connecting...");
        ((SillyApp) currentActivity.getApplication()).getDependencyComponent().inject(this);
        yesNoDialog = new YesNoDialog();
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        //super.onProgressUpdate(values);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Integer... params) {
        publishProgress(params);
        setApi(getAPI_URL());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        super.onPostExecute(aVoid);

    }

    public abstract void Response(String response);

    public abstract Map<String,String> getListParams();

    public abstract String getAPI_URL();

    public void setApi(final String API_URL){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RequestQueue queue = volleyService.getRequestQueue(currentActivity.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Response(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressAsyncTask.getProgressDialog().dismiss();
                yesNoDialog.show(((AppCompatActivity)currentActivity).getSupportFragmentManager(),"network dialog");
                yesNoDialog.setOnClickListener(new YesNoDialog.Listener() {
                    @Override
                    public void onClick() {
                        setApi(API_URL);
                    }
                });
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return getListParams();
            }
        };
        queue.add(stringRequest);
    }
}
