package khoavin.sillylearningenglish.FUNCTION.TrainingRoom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import khoavin.sillylearningenglish.PATTERN.TabFragmentPattern;

/**
 * Created by KhoaVin on 1/23/2017.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<TabFragmentPattern> listTabPage;
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        this.listTabPage = new ArrayList<TabFragmentPattern>();
    }
    public void addTab(TabFragmentPattern tabFragment){

        this.listTabPage.add(tabFragment);
    }
    @Override
    public Fragment getItem(int position) {

        return listTabPage.get(position);
    }

    @Override
    public int getCount() {
        return listTabPage.size();
    }
}
