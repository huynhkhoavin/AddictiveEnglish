package khoavin.sillylearningenglish.FUNCTION.TrainingRoom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.PATTERN.AdapterPattern;
import khoavin.sillylearningenglish.ENTITY_DATABASE.Lesson;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 1/24/2017.
 */

public class PodcastListAdapter extends AdapterPattern {
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
        Lesson[] listeningUnits = (Lesson[]) getDataSource();
        mViewHolder.level.setText("LEVEL "+String.valueOf(listeningUnits[position].getLs_level()));
        mViewHolder.title.setText(listeningUnits[position].getLs_title());
        mViewHolder.chapter.setText("10");
        mViewHolder.exp.setText("200");
        mViewHolder.source.setText("BookWorm");
    }

}
