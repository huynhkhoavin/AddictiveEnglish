package khoavin.sillylearningenglish.Function.Social.SocialFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.FragmentPattern;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_social,container,false);
        ButterKnife.bind(this,v);

        setUpTabAdapter();
        return v;
    }
    private void setUpTabAdapter(){
        String[] TabTitle = {"Home","Profile"};
        FragmentPattern[] FragmentList = {new SocialHomeFragment(),new SocialProfileFragment()};
        tabPagerAdapter = new ViewPagerAdapter( getActivity().getSupportFragmentManager(),TabTitle,FragmentList,getActivity());
        socialViewPager.setAdapter(tabPagerAdapter);
        socialTabLayout.setupWithViewPager(socialViewPager);
        socialTabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
    }
}
