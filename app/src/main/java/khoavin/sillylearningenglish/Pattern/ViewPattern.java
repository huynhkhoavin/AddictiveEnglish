package khoavin.sillylearningenglish.Pattern;

import android.app.Activity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Khoavin on 3/12/2017.
 */

public class ViewPattern{
    public Activity activity;
    public ViewPattern(Activity activity){
        this.activity = activity;
        ButterKnife.bind(activity);
    }
}
