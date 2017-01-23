package khoavin.sillylearningenglish.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import khoavin.sillylearningenglish.ToolFactory.IViewHolder;

/**
 * Created by KhoaVin on 1/19/2017.
 */

public class AdapterPattern <T> extends BaseAdapter {
    private Context mContext;
    private T[] dataSource;
    private IViewHolder viewHolder;

    public AdapterPattern(Context c, T[] dataSource, IViewHolder viewHolder){
        mContext = c;
        dataSource = dataSource;
        viewHolder = viewHolder;
    }


    @Override
    public int getCount() {
        return dataSource.length;
    }

    @Override
    public Object getItem(int position) {
        return dataSource[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
