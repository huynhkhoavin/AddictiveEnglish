package khoavin.sillylearningenglish.NetworkService.Interfaces;

import android.content.Context;

import com.android.volley.RequestQueue;

/**
 * Created by KhoaVin on 09/05/2017.
 */

public interface IVolleyService {
    RequestQueue getRequestQueue(Context context);
}

