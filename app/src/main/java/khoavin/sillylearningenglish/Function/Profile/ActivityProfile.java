package khoavin.sillylearningenglish.Function.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.TrainingCategoryFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.DailyLesson.DailyLessonFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Storage.UserStorageFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.YoutubeChanel.ChannelFragment;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 10/07/2017.
 */

public class ActivityProfile extends BaseDetailActivity {
    @BindView(R.id.titleBar)
    LinearLayout titleBar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ViewPagerAdapter tabPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        setupWindowAnimations();

        ButterKnife.bind(this);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SetupAdapter();
    }

    public void SetupAdapter(){
        String[] TabTitle = {"Info","Lesson"};
        FragmentPattern[] FragmentList = {new FragmentHistory(),new FragmentTrainingProfile()};
        tabPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),TabTitle,FragmentList,this);
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
    private void setupWindowAnimations() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }
    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        // This view will not be affected by enter transition animation
        //enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Slide enterTransition = new Slide(Gravity.RIGHT);
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        return enterTransition;
    }
}
