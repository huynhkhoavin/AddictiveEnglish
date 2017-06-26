package khoavin.sillylearningenglish.Pattern;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import dmax.dialog.SpotsDialog;

/**
 * Created by Khoavin on 3/19/2017.
 */

public abstract class ProgressAsyncTask extends AsyncTask<Integer, Integer, Void> {
    AlertDialog progressDialog;
    public ProgressAsyncTask(Context context){
        this.progressDialog = new SpotsDialog(context);

        progressDialog.setTitle("Connecting...");
        //progressDialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    public abstract void onDoing();

    public Void OnDoing(){
        return null;
    }
    public abstract void onTaskComplete(Void aVoid);
    @Override
    protected Void doInBackground(Integer... params) {
        publishProgress(params);

        onDoing();
        OnDoing();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        super.onPostExecute(aVoid);
        onTaskComplete(aVoid);
    }
    public void setProgressTitle(String title){
        this.progressDialog.setTitle(title);
    }
    public AlertDialog getProgressDialog() {
        return progressDialog;
    }
}
