package khoavin.sillylearningenglish.Function.HomeMenu.HomePage;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.BindView;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.TrainingCategoryFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeFragment;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.Pattern.ViewPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 19/05/2017.
 */

public class HomePageView extends ViewPattern {
    private ViewPagerAdapter tabPagerAdapter;

    @BindView(R.id.home_ViewPager)
    ViewPager viewPager;

    @BindView(R.id.home_TabLayout)
    TabLayout tabLayout;
    private FragmentActivity mainActivity;

    public HomePageView(Activity activity) {
        super(activity);
        mainActivity = (FragmentActivity) activity;
        setUpTabAdapter();
    }
    public HomePageView(Activity activity,View v){
        super(v);
        mainActivity = (FragmentActivity) activity;
        setUpTabAdapter();
    }
    //region METHOD
    private void setUpTabAdapter(){
        String[] TabTitle = {"Trang Chủ","Phân Loại","Của Tôi"};
        FragmentPattern[] FragmentList = {new TrainingHomeFragment(),new TrainingCategoryFragment(),new TrainingHomeFragment()};
        tabPagerAdapter = new ViewPagerAdapter( mainActivity.getSupportFragmentManager(),TabTitle,FragmentList,mainActivity);
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
    //endregion
}
