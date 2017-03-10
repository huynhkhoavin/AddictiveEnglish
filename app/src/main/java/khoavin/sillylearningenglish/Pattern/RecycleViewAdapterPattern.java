package khoavin.sillylearningenglish.Pattern;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;

/**
 * Created by KhoaVin on 1/19/2017.
 */

public abstract class RecycleViewAdapterPattern extends RecyclerView.Adapter {
    protected LayoutInflater mLayoutInflater;
    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<Object> getDataSource() {
        return dataSource;
    }

    public void setDataSource(ArrayList<Object> dataSource) {
        this.dataSource = null;
        this.dataSource = dataSource;
    }

    private Context mContext;
    private ArrayList<Object> dataSource;

    public RecycleViewAdapterPattern(Context mContext, ArrayList<Object> dataSource){
        this.mContext = mContext;
        this.dataSource = dataSource;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
