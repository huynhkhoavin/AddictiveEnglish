package khoavin.sillylearningenglish.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by KhoaVin on 2/10/2017.
 */
public class MyFirebaseInstanceIDService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
