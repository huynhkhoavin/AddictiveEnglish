package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import khoavin.sillylearningenglish.NetworkService.Interfaces.IVolleyService;

/**
 * Created by KhoaVin on 09/05/2017.
 */

public class VolleyService implements IVolleyService {
    private static final String TAG = "VolleyService";
    private RequestQueue mRequestQueue;

    public VolleyService() {
    }

    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }
}