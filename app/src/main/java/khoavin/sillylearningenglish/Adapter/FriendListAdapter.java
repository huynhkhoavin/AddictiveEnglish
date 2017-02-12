package khoavin.sillylearningenglish.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


/**
 * Created by KhoaVin on 2/12/2017.
 */

public class FriendListAdapter extends AdapterPattern {
    public FriendListAdapter(Context mContext, Object[] dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
