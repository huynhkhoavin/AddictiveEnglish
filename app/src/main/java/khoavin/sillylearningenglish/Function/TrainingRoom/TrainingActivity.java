package khoavin.sillylearningenglish.Function.TrainingRoom;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

public class TrainingActivity extends BaseDetailActivity {
    //private BottomBar mBottomBar;

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    private ViewPager viewPager;
    private ViewPagerAdapter tabPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        ButterKnife.bind(this);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        setUpViewPager(savedInstanceState);
        setUpTabAdapter(savedInstanceState);
//        setUpBottomBar(savedInstanceState);

        setupWindowAnimations();
    }
    //region METHOD
    private void setUpTabAdapter(Bundle savedInstanceState){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visibility returnTransition = buildReturnTransition();
                getWindow().setReturnTransition(returnTransition);

                finishAfterTransition();
            }
        });
        edtSearch.clearFocus();
        String[] TabTitle = {"Home","Category","Daily","Youtube","My Lesson"};
        FragmentPattern[] FragmentList = {new TrainingHomeFragment(),new TrainingCategoryFragment(), new DailyLessonFragment(), new ChannelFragment(),new UserStorageFragment()};
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
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
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
