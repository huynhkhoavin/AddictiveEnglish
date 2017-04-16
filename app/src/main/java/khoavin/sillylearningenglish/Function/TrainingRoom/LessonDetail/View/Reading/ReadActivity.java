package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.Reading;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.R;

public class ReadActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    InfiniteViewPagerAdapter infiniteViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);
        FragmentPattern[] FragmentList = {new FragmentA(),new FragmentB(), new FragmentA()} ;
        infiniteViewPagerAdapter = new InfiniteViewPagerAdapter(getSupportFragmentManager(),FragmentList);
        viewPager.setAdapter(infiniteViewPagerAdapter);
    }
}
