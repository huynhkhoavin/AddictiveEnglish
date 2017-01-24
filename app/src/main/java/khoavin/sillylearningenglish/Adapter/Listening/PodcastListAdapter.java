package khoavin.sillylearningenglish.Adapter.Listening;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import khoavin.sillylearningenglish.Adapter.AdapterPattern;
import khoavin.sillylearningenglish.EntityDatabase.Silly_english.ListeningUnit;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.ViewHolder.PodcastItemViewHolder;
import khoavin.sillylearningenglish.ViewHolder.ViewHolder;

/**
 * Created by KhoaVin on 1/24/2017.
 */

public class PodcastListAdapter extends AdapterPattern {

    private LayoutInflater mLayoutInflater;
    public PodcastListAdapter(Context mContext, Object[] dataSource) {
        super(mContext, dataSource);
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.single_podcast_item, parent, false);
        return new PodcastItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PodcastItemViewHolder mViewHolder = (PodcastItemViewHolder)holder;
        ListeningUnit[] listeningUnits = (ListeningUnit[]) getDataSource();
        mViewHolder.level.setText("LEVEL "+String.valueOf(listeningUnits[position].getLu_level()));
        mViewHolder.title.setText(listeningUnits[position].getLu_title());
        mViewHolder.chapter.setText("10");
        mViewHolder.exp.setText("200");
        mViewHolder.source.setText("BookWorm");
    }

}
