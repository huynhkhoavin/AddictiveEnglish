package khoavin.sillylearningenglish.Activity;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import khoavin.sillylearningenglish.R;

public class DemoNavigationActivity extends AppCompatActivity {
    private BottomBar mBottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_navigation);
        mBottomBar = BottomBar.attach(this, savedInstanceState,
                Color.parseColor("#f2f2f2"), // Background Color
                ContextCompat.getColor(getBaseContext(), R.color.colorAccent), // Tab Item Color
                0.25f); // Tab Item Alpha
        mBottomBar.setItems(R.menu.listening_bottom_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                //showToast(menuItemId, false);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                //showToast(menuItemId, true);
            }
        });

    }
}
