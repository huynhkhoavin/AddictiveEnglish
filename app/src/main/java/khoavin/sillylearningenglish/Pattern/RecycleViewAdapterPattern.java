package khoavin.sillylearningenglish.Pattern;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;

/**
 * Created by KhoaVin on 1/19/2017.
 */

public abstract class RecycleViewAdapterPattern extends RecyclerView.Adapter {

    /**
     * The tag
     */
    public final String TAG = "AdapterPattern";

    /**
     * The layout inflater
     */
    protected LayoutInflater mLayoutInflater;

    /**
     * The AdapterOnItemClick
     */
    public AdapterOnItemClick adapterOnItemClick;

    /**
     * Set Click item event
     * @param adapterOnItemClick
     */
    public void setAdapterOnItemClick(AdapterOnItemClick adapterOnItemClick) {
        this.adapterOnItemClick = adapterOnItemClick;
    }

    /**
     * The context
     */
    private Context mContext;

    /**
     * The data source
     */
    private ArrayList<Object> dataSource;

    /**
     * Get the context
     * @return
     * The View Context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Set the context
     * @param mContext
     * The View Context
     */
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Get the data source
     * @return
     * The data source as array list object
     */
    public ArrayList<Object> getDataSource() {
        return dataSource;
    }

    /**
     * Set the data source
     * @param dataSource
     * The data source as array list object
     */
    public void setDataSource(ArrayList<Object> dataSource) {
        this.dataSource = null;
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    /**
     * Clear data source
     */
    public void clearDataSource(){
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

    /**
     * Get item position
     * @param obj
     * The object want to get position
     * @return
     * The position of obj
     */
    public int getItemPosition(Object obj)
    {
        if(dataSource.contains(obj))
        {
            return dataSource.indexOf(obj);
        }
        else
        {
            return -1;
        }
    }

    /**
     * Update data source
     * @param position
     * The position of updated obj
     * @param obj
     * The obj
     */
    public void updateDataSource(int position, Object obj){
        this.dataSource.set(position,obj);
        notifyDataSetChanged();
    }

    /**
     * Add item to data source
     * @param object
     * The added item
     */
    public void addDataSource(Object object)
    {
        this.dataSource.add(object);
        notifyDataSetChanged();
    }

    /**
     * Initialize
     * @param mContext
     * The View Context
     * @param dataSource
     * The data source
     */
    public RecycleViewAdapterPattern(Context mContext, ArrayList<Object> dataSource){
        this.mContext = mContext;
        this.dataSource = dataSource;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * Get item count
     * @return
     * The size of data source
     */
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
