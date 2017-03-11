package khoavin.sillylearningenglish.Function.TrainingRoom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.DatabaseEntity.Lesson;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by KhoaVin on 1/24/2017.
 */

public class PodcastListRecycleViewAdapter extends RecycleViewAdapterPattern {
    public PodcastListRecycleViewAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.single_podcast, parent, false);
        return new PodcastItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PodcastItemViewHolder mViewHolder = (PodcastItemViewHolder)holder;
        ArrayList<Lesson> listeningUnits = ArrayConvert.toArrayList(getDataSource());
        mViewHolder.level.setText("LEVEL "+String.valueOf(listeningUnits.get(position).getLs_level()));
        mViewHolder.title.setText(listeningUnits.get(position).getLs_title());
        mViewHolder.chapter.setText("10");
        mViewHolder.exp.setText("200");
        mViewHolder.source.setText("BookWorm");
    }

}
