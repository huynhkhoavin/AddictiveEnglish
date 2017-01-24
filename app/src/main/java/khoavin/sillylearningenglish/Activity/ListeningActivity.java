package khoavin.sillylearningenglish.Activity;

import android.support.annotation.IdRes;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import khoavin.sillylearningenglish.Adapter.TabPagerAdapter;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.TabFragment.PodcastTabFragment;
import khoavin.sillylearningenglish.TabFragment.StoryTabFragment;

public class ListeningActivity extends AppCompatActivity {
    private BottomBar mBottomBar;
    private ViewPager viewPager;
    private TabPagerAdapter tabPagerAdapter;
    private ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        setUpViewPager(savedInstanceState);
        setUpTabAdapter(savedInstanceState);
        setUpActionBar(savedInstanceState);
        setUpBottomBar(savedInstanceState);

    }

    //region METHOD
    private void setUpTabAdapter(Bundle savedInstanceState){
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());

        tabPagerAdapter.addTab(new PodcastTabFragment());
        tabPagerAdapter.addTab(new StoryTabFragment());
        tabPagerAdapter.addTab(new PodcastTabFragment());
        tabPagerAdapter.addTab(new StoryTabFragment());
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
    private void setUpActionBar(Bundle savedInstanceState){
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

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

}
