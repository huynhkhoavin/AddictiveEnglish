package khoavin.sillylearningenglish.Pattern;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by KhoaVin on 13/05/2017.
 */

public abstract class ProgressThreadTask extends AsyncTask<Integer, Integer, Void> {
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
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onTaskComplete(aVoid);
    }
}
