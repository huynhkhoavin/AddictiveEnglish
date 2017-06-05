package khoavin.sillylearningenglish.Function.Social.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Comment;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.TimeParse;

/**
 * Created by KhoaVin on 02/06/2017.
 */

public class CommentAdapter extends RecycleViewAdapterPattern {
    /**
     * Initialize
     *
     * @param mContext   The View Context
     * @param dataSource
     */
    public CommentAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_comment_item,parent,false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder mViewHolder = (CommentViewHolder) holder;
        final ArrayList<Comment> comments = ArrayConvert.toArrayList(getDataSource());
        Glide.with(getContext())
                .load(comments.get(position).getAvatarUrl())
                .placeholder(R.drawable.avatar_holder)
                .into(mViewHolder.userAvatar);
        mViewHolder.tvContent.setText(comments.get(position).getCommentContent());

        String timeParse = null;
        try {
            timeParse = TimeParse.getTimeAgo(comments.get(position).getDuration());
            mViewHolder.tvDuration.setText(timeParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        mViewHolder.tvUserName.setText(comments.get(position).getName());
    }
}
