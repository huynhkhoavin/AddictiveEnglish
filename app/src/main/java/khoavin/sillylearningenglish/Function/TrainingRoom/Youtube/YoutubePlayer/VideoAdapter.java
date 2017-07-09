package khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.YoutubePlayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Model.Item;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by KhoaVin on 08/07/2017.
 */

public class VideoAdapter extends RecycleViewAdapterPattern {
    /**
     * Initialize
     *
     * @param mContext   The View Context
     * @param dataSource
     */
    public VideoAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.single_youtube_video,parent,false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ArrayList<Item> listVideo = ArrayConvert.toArrayList(getDataSource());

        VideoViewHolder videoViewHolder = (VideoViewHolder)holder;

        Glide.with(getContext()).load(listVideo.get(position).getSnippet().getThumbnails().getMedium().getUrl()).placeholder(R.drawable.video_placeholder).into(videoViewHolder.videoImage);

        videoViewHolder.videoTitle.setText(listVideo.get(position).getSnippet().getTitle());

        videoViewHolder.channelName.setText(listVideo.get(position).getSnippet().getChannelTitle());
        videoViewHolder.singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position,listVideo.get(position));
            }
        });
    }
}
