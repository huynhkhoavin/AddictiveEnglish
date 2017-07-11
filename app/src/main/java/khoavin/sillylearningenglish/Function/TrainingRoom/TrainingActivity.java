package khoavin.sillylearningenglish.Function.TrainingRoom;

import android.content.Intent;
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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.TrainingCategoryFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.DailyLesson.DailyLessonFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.SearchLesson.ActivitySearchLesson;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Storage.UserStorageFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.YoutubeChanel.ChannelFragment;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.Transition.BaseDetailActivity;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_SEARCH_KEY;

public class TrainingActivity extends BaseDetailActivity {
    //private BottomBar mBottomBar;

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.btnSearch)
    ImageView btnSearch;
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


        //search lesson
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSearch.getText().toString().equals("")){
                    Toast.makeText(TrainingActivity.this, "Nothing to search", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String s = edtSearch.getText().toString();
                    Intent it = new Intent(TrainingActivity.this, ActivitySearchLesson.class);

                    Storage.getInstance().addValue(CURRENT_SEARCH_KEY,s);

                    startActivity(it);
                }
            }
        });


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
        String[] TabTitle = {"Home","Category","Daily","My Lesson","Youtube Channel"};
        FragmentPattern[] FragmentList = {new TrainingHomeFragment(),new TrainingCategoryFragment(), new DailyLessonFragment(),new UserStorageFragment(), new ChannelFragment()};
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
