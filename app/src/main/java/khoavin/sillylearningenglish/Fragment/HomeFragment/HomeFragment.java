package khoavin.sillylearningenglish.Fragment.HomeFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.Adapter.HomeViewPagerAdapter;
import khoavin.sillylearningenglish.Fragment.TabFragment;
import khoavin.sillylearningenglish.R;

import static khoavin.sillylearningenglish.R.id.viewPager;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class HomeFragment extends TabFragment {
    private ViewPager mViewPager;
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.home_fragment,container,false);
        mViewPager = (ViewPager)v.findViewById(viewPager);
        tabLayout = (TabLayout)v.findViewById(R.id.tab_layout);
        homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager());
        //region VIEWPAGER
        mViewPager.setAdapter(homeViewPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(homeViewPagerAdapter);
        //endregion
        return v;
    }
}
