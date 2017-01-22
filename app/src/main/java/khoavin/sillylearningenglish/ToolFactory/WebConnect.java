package khoavin.sillylearningenglish.ToolFactory;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import khoavin.sillylearningenglish.Constant.WebConstant;

import static khoavin.sillylearningenglish.Constant.WebConstant.WEBSERVICE_ADDRESS;

/**
 * Created by KhoaVin on 27/05/2016.
 */
public abstract class WebConnect extends AsyncTask<String, String, String>
{
    HttpURLConnection connection = null;
    URL url;
    String action;
    String parameterString;
    String result = "";
    public ProgressDialog pDialog;
    public String getResult()
    {
        return result;
    }
    public void setAction(String action, String parameter)
    {
        this.action = action;
        this.parameterString = parameter;
    }
    @Override
    protected void onPreExecute() {
        preDoing();
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {

        String s = Post(params[0]);
        return s;
    }
    public String Post(String u)
    {
        connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;
        String s = WEBSERVICE_ADDRESS+u;
        try {
            URL url = new URL(s);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");

            String urlParameters = "action="+action+"&"+"data="+parameterString;
            //String tmp = new String(urlParameters,"UTF-8");
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return u;
    }
    private final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

    public String getResult(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        Matcher m = REMOVE_TAGS.matcher(string);
        return m.replaceAll("").replaceAll("(^\\s+|\\s+$)", "");
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        result = getResult(s);
        postExecuted(result);
        System.out.println("Kết quả là:" + result);
    }
    public abstract void  postExecuted(String s);
    public void preDoing(){};
    public void postDoing(){};// không được viết hàm thay đổi UI ở đây vì chạy dưới nền
}