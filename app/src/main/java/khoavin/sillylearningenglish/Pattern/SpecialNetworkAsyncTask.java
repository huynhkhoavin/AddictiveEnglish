package khoavin.sillylearningenglish.Pattern;

import android.app.Activity;

import java.util.Map;

/**
 * Created by KhoaVin on 30/06/2017.
 */

public class SpecialNetworkAsyncTask extends NetworkAsyncTask {
    public SpecialNetworkAsyncTask(Activity currentActivity) {
        super(currentActivity);
    }

    @Override
    public void Response(String response) {

    }

    @Override
    public Map<String, String> getListParams() {
        return null;
    }

    @Override
    public String getAPI_URL() {
        return null;
    }
}
