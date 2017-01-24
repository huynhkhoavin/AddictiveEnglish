package khoavin.sillylearningenglish.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import khoavin.sillylearningenglish.ViewHolder.ViewHolder;

/**
 * Created by KhoaVin on 1/19/2017.
 */

public abstract class AdapterPattern extends RecyclerView.Adapter {
    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Object[] getDataSource() {
        return dataSource;
    }

    public void setDataSource(Object[] dataSource) {
        this.dataSource = dataSource;
    }

    private Context mContext;
    private Object[] dataSource;

    public AdapterPattern(Context mContext,Object[] dataSource){
        this.mContext = mContext;
        this.dataSource = dataSource;
    }
    @Override
    public int getItemCount() {
        return dataSource.length;
    }
}
