package khoavin.sillylearningenglish.Pattern;

/**
 * Created by Khoavin on 3/19/2017.
 */

public interface AsynctaskListener {
    void onPreExecute();

    Void doInBackground(Integer... params);

    void onPostExecute(Void aVoid);

    void onProgressUpdate(Integer... values);
}
