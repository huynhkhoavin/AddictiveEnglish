package khoavin.sillylearningenglish.Function.Social.SocialFragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.TrainingCategoryFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.DailyLesson.DailyLessonFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeFragment;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Storage.UserStorageFragment;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
import khoavin.sillylearningenglish.Pattern.TabIconPagerAdapter;
import khoavin.sillylearningenglish.Pattern.ViewPagerAdapter;
import khoavin.sillylearningenglish.R;

/**
 * Created by Dev02 on 5/29/2017.
 */

public class SocialFragment extends FragmentPattern {
    ViewPagerAdapter tabPagerAdapter;
    @BindView(R.id.social_TabLayout)
    TabLayout socialTabLayout;
    @BindView(R.id.social_ViewPager)
    ViewPager socialViewPager;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton btnPost;
    private ArrayList<Pair> piecesAndButtons = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_social,container,false);
        ButterKnife.bind(this,v);
        //setupBoomButton();
        setUpTabAdapter();

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                PostNotifyFragment dialogFragment = new PostNotifyFragment();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
        return v;
    }
    private void setUpTabAdapter(){
        String[] TabTitle = {"Home","Profile"};
        FragmentPattern[] FragmentList = {new SocialHomeFragment(),new SocialProfileFragment()};
        //TabIconPagerAdapter(FragmentManager fm, Context context, FragmentPattern[] listFragment,int[] imageResId,String[] tabTitles) {
        tabPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),TabTitle,FragmentList,getContext());
        socialViewPager.setAdapter(tabPagerAdapter);
        socialTabLayout.setupWithViewPager(socialViewPager);
        socialTabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
}
