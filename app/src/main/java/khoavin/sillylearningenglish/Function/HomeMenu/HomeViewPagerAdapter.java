package khoavin.sillylearningenglish.Function.HomeMenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import khoavin.sillylearningenglish.Function.HomeMenu.HomeFragment.Fighting.View.FightingFragment;
import khoavin.sillylearningenglish.Function.HomeMenu.HomeFragment.Training.TrainingFragment;

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
                title="Chinh Chiến";
                break;
            case 1:
                title="Luyện Tập";
                break;
        }
        return title;
    }
}
