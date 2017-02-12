package khoavin.sillylearningenglish.PATTERN;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

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
