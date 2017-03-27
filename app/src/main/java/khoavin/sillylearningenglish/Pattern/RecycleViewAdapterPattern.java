package khoavin.sillylearningenglish.Pattern;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;

/**
 * Created by KhoaVin on 1/19/2017.
 */

public abstract class RecycleViewAdapterPattern extends RecyclerView.Adapter {
    public final String TAG = "AdapterPattern";
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
        notifyDataSetChanged();
    }
    public void ClearDataSource(){
        if (dataSource.size()==0)
        {
            return;
        }
        int size = this.dataSource.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.dataSource.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }
    public void UpdateDataSource(int position, Object obj){
        this.dataSource.set(position,obj);
        notifyDataSetChanged();
    }
    public void addDataSource(Object object)
    {
        this.dataSource.add(object);
        notifyDataSetChanged();
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
        try {
            return dataSource.size();
        }
        catch (Exception ex)
        {
            Log.e(TAG,"DataSource is Null!");
            return 0;
        }
    }
}
