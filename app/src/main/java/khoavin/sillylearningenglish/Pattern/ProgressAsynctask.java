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
        progressDialog.setTitle("Connecting To Chat Service...");
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    public abstract void onDoing();
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
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }
}
