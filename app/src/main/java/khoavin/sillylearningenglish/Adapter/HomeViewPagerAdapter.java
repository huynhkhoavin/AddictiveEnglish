package khoavin.sillylearningenglish.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import khoavin.sillylearningenglish.Fragment.HomeFragment.FightingFragment;
import khoavin.sillylearningenglish.Fragment.HomeFragment.TrainingFragment;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new FightingFragment();
            case 1: return new TrainingFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Luyện Tập";
                break;
            case 1:
                title="Chinh Chiến";
                break;
        }
        return title;
    }
}
