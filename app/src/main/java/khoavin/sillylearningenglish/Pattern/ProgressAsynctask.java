package khoavin.sillylearningenglish.Pattern;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

/**
 * Created by Khoavin on 3/19/2017.
 */

public abstract class ProgressAsynctask extends AsyncTask<Integer, Integer, Void> {
    ProgressDialog progressDialog;

    public void setContext(Context context){
        this.progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Connecting...");
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    public abstract void onDoing();
    public abstract void onTaskComplete(Void aVoid);
    @Override
    protected Void doInBackground(Integer... params) {
        publishProgress(params);
        onDoing();
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

}
