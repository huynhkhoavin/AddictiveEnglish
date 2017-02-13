package khoavin.sillylearningenglish.FUNCTION.HomeMenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import khoavin.sillylearningenglish.FUNCTION.HomeMenu.HomeFragment.FightingFragmentPattern;
import khoavin.sillylearningenglish.FUNCTION.HomeMenu.HomeFragment.TrainingFragmentPattern;

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
            case 0: return new FightingFragmentPattern();
            case 1: return new TrainingFragmentPattern();
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
                title="Chinh Chiến";
                break;
            case 1:
                title="Luyện Tập";
                break;
        }
        return title;
    }
}
