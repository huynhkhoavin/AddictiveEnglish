package khoavin.sillylearningenglish.Function.TrainingRoom;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.TrainingCategoryFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.DailyLesson.DailyLessonFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Storage.UserStorageFragment;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
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
        String[] TabTitle = {"Home","Category","My Lesson","Daily Lesson"};
        FragmentPattern[] FragmentList = {new TrainingHomeFragment(),new TrainingCategoryFragment(),new UserStorageFragment(), new DailyLessonFragment()};
        tabPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),TabTitle,FragmentList,this);
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
    private void setUpViewPager(Bundle savedInstanceState){
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
    }
    //endregion

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

    }
}
