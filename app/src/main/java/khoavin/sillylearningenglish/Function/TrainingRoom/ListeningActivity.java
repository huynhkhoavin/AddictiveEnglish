package khoavin.sillylearningenglish.Function.TrainingRoom;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;

public class ListeningActivity extends AppCompatActivity {
    private BottomBar mBottomBar;
    private ViewPager viewPager;
    private ViewPagerAdapter tabPagerAdapter;
    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right)
                .replace(R.id.activity_listening, fragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);

        //replaceFragment(new TrainingHomeFragment());

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        setUpViewPager(savedInstanceState);
        setUpTabAdapter(savedInstanceState);
        setUpBottomBar(savedInstanceState);

    }

    //region METHOD
    private void setUpTabAdapter(Bundle savedInstanceState){
        String[] ListTitle = {"PodCast","Story"};
        FragmentPattern[] ListFragment = {new PodcastFragment(),new PlayingFragment() };
        tabPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),ListTitle,ListFragment);
        viewPager.setAdapter(tabPagerAdapter);
    }
    private void setUpViewPager(Bundle savedInstanceState){
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position,true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setUpBottomBar(Bundle savedInstanceState){
        mBottomBar = BottomBar.attach(this, savedInstanceState);
//                ,Color.parseColor("#8CDA87"), // Background Color
//                ContextCompat.getColor(this, R.color.colorIcon), // Tab Item Color
//                0.25f); // Tab Item Alpha

        mBottomBar.setItems(R.menu.listening_bottom_menu);
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorPrimary));
        mBottomBar.mapColorForTab(1, "#8CDA87");
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {

            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId){
                    case R.id.like:{
                        viewPager.setCurrentItem(0);
                    }
                    break;
                    case R.id.love:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.sad:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.angry:
                        viewPager.setCurrentItem(3);
                }

            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {


            }
        });
    }
    //endregion

    //region Override
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

    }
}
