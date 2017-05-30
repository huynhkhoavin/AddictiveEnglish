package khoavin.sillylearningenglish.Function.Social.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 29/05/2017.
 */

public class NotificationAdapter extends RecycleViewAdapterPattern {
    /**
     * Initialize
     *
     * @param mContext   The View Context
     * @param dataSource
     */
    public NotificationAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_homepage_item,parent,false);

        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
