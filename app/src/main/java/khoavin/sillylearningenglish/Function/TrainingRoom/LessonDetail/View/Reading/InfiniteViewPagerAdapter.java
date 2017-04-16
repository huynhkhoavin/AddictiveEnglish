package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.Reading;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import khoavin.sillylearningenglish.Pattern.FragmentPattern;

/**
 * Created by Khoavin on 4/13/2017.
 */

public class InfiniteViewPagerAdapter extends FragmentPagerAdapter {
    public FragmentPattern[] listFragment;
    public InfiniteViewPagerAdapter(FragmentManager fm, FragmentPattern[] listFragment) {
        super(fm);
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment[position];
    }

    @Override
    public int getItemPosition(Object object) {
        int x  = super.getItemPosition(object);
        return x;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
