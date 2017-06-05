package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import khoavin.sillylearningenglish.R;

/**
 * Created by OatOal on 6/4/2017.
 */

public class MailFilterAdapter extends BaseAdapter {
    LayoutInflater inflator;
    String[] mTitleSources;

    public MailFilterAdapter(Context context, String[] source) {
        inflator = LayoutInflater.from(context);
        mTitleSources = source;
    }

    @Override
    public int getCount() {
        return mTitleSources.length;
    }

    @Override
    public Object getItem(int position) {
        if(mTitleSources == null) return null;
        if(position >= 0 && position < mTitleSources.length)
            return mTitleSources[position];
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.single_filter_item, null);
        TextView tv = (TextView) convertView.findViewById(R.id.filter_title);
        tv.setText(mTitleSources[position]);
        return convertView;
    }
}
