package khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View.ProgressListViewHolder;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.YoutubeChannel;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by KhoaVin on 08/07/2017.
 */

public class YoutubeChannelAdapter extends RecycleViewAdapterPattern {
    /**
     * Initialize
     *
     * @param mContext   The View Context
     * @param dataSource
     */
    public YoutubeChannelAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_youtube_channel,parent,false);
        return new YoutubeChannelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ArrayList<YoutubeChannel> list = ArrayConvert.toArrayList(getDataSource());
        final YoutubeChannelViewHolder mViewHolder = (YoutubeChannelViewHolder) holder;

        Glide.with(getContext()).load(list.get(position).getYcUrl()).into(mViewHolder.channelImage);
        mViewHolder.channelName.setText(list.get(position).getYcName());
        mViewHolder.channelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position,list.get(position));
            }
        });



    }
}
