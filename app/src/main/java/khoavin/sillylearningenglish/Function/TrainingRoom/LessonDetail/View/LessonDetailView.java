package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.Pattern.ViewPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by Khoavin on 4/8/2017.
 */

public class LessonDetailView extends ViewPattern {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ViewPagerAdapter tabPagerAdapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    public LessonDetailView(Activity activity) {
        super(activity);
        ButterKnife.bind(this,activity);
        setUpTabAdapter();
    }
    private void setUpTabAdapter(){
        String[] TabTitle = {"Play","Tiến Trình"};
        LessonReadFragment lessonRead = new LessonReadFragment();
        LessonProgressFragment lessonProgress = new LessonProgressFragment();
        FragmentPattern[] FragmentList = {new LessonReadFragment(),new LessonProgressFragment()};
        tabPagerAdapter = new ViewPagerAdapter(((AppCompatActivity)activity).getSupportFragmentManager(),TabTitle,FragmentList);
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
}
