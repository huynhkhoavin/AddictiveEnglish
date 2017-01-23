package khoavin.sillylearningenglish.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import khoavin.sillylearningenglish.TabFragment.ITabFragment;
import khoavin.sillylearningenglish.TabFragment.PodcastTabFragment;
import khoavin.sillylearningenglish.TabFragment.StoryTabFragment;
import khoavin.sillylearningenglish.TabFragment.TabFragment;

/**
 * Created by KhoaVin on 1/23/2017.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<TabFragment> listTabPage;
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        this.listTabPage = new ArrayList<TabFragment>();
    }
    public void addTab(TabFragment tabFragment){

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
