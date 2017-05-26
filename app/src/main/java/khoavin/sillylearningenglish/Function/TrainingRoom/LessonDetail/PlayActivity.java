package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment.LessonPlayFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.Fragment.LessonProgressFragment;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.TabIconPagerAdapter;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class PlayActivity extends AppCompatActivity {
    public Lesson lesson;
    public String TAG = "Play Activity";
    //Region View
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TabIconPagerAdapter tabPagerAdapter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    LessonPlayFragment lessonReadFragment;
    LessonProgressFragment lessonProgressFragment;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        setUpTabAdapter();
    }
    private void setUpTabAdapter(){
        lessonReadFragment = new LessonPlayFragment();
        lessonProgressFragment = new LessonProgressFragment();
        lessonProgressFragment.setParentViewPager(viewPager);
        FragmentPattern[] FragmentList = {lessonReadFragment,lessonProgressFragment};
        tabPagerAdapter = new TabIconPagerAdapter(((AppCompatActivity)this).getSupportFragmentManager(),getApplicationContext(),FragmentList,new int[]{R.drawable.ic_audio_book,R.drawable.ic_progress}, new String[]{"Play","Progress"});
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
