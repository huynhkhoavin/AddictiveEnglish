package khoavin.sillylearningenglish.FUNCTION.TrainingRoom;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import khoavin.sillylearningenglish.FUNCTION.TrainingRoom.Home.TrainingHomeFragment;
import khoavin.sillylearningenglish.PATTERN.FragmentPattern;
import khoavin.sillylearningenglish.PATTERN.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;

public class TrainingActivity extends AppCompatActivity {
    //private BottomBar mBottomBar;
    private ViewPager viewPager;
    private ViewPagerAdapter tabPagerAdapter;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        setUpViewPager(savedInstanceState);
        setUpTabAdapter(savedInstanceState);
//        setUpBottomBar(savedInstanceState);
    }
    //region METHOD
    private void setUpTabAdapter(Bundle savedInstanceState){
        String[] TabTitle = {"Trang Chủ","Phân Loại","Của Tôi"};
        FragmentPattern[] FragmentList = {new TrainingHomeFragment(),new TrainingHomeFragment(),new TrainingHomeFragment()};
        tabPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),TabTitle,FragmentList);
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
    private void setUpViewPager(Bundle savedInstanceState){
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //mBottomBar.selectTabAtPosition(position,true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //endregion

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

    }
}
