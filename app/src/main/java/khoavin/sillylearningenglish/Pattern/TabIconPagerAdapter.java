package khoavin.sillylearningenglish.Pattern;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import khoavin.sillylearningenglish.R;


/**
 * Created by KhoaVin on 21/05/2017.
 */

public class TabIconPagerAdapter extends FragmentPagerAdapter {
    public FragmentPattern[] listFragment;
    private int[] imageResId;
    public String[] tabTitles;
    private Context context;

    public TabIconPagerAdapter(FragmentManager fm, Context context, FragmentPattern[] listFragment,int[] imageResId,String[] tabTitles) {
        super(fm);
        this.context = context;
        this.listFragment = listFragment;
        this.imageResId = imageResId;
        this.tabTitles = tabTitles;
    }

    @Override
    public int getCount() {
        return listFragment.length;
    }

    @Override
    public Fragment getItem(int position) {
        if (listFragment.length>0) {
            return listFragment[position];
        }
        else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
