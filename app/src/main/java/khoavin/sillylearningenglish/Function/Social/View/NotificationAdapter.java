package khoavin.sillylearningenglish.Function.Social.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.AdapterOnItemClick;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Notification;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.TimeParse;

/**
 * Created by KhoaVin on 29/05/2017.
 */

public class NotificationAdapter extends RecycleViewAdapterPattern {
    public NotificationAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_notification_item,parent,false);

        return new NotificationViewHolder(itemView);
    }
    AdapterOnItemClick doLikeListener;

    public void setDoLikeListener(AdapterOnItemClick doLikeListener) {
        this.doLikeListener = doLikeListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NotificationViewHolder mViewHolder = (NotificationViewHolder) holder;
        final ArrayList<Notification> notifications = ArrayConvert.toArrayList(getDataSource());
        Glide.with(getContext())
                .load(notifications.get(position).getAvatarUrl())
                .placeholder(R.drawable.avatar_holder)
                .into(mViewHolder.userAvatar);
        mViewHolder.userName.setText(notifications.get(position).getName());
        mViewHolder.userLocation.setText(notifications.get(position).getUserLocation());
        mViewHolder.notifyContent.setText(notifications.get(position).getNotifyContent());


        try {
            mViewHolder.postTime.setText(TimeParse.getTimeAgo(notifications.get(position).getDuration()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mViewHolder.tvLikeCount.setText(notifications.get(position).getLikeCount());
        mViewHolder.commentCount.setText(notifications.get(position).getCommentCount());
        mViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position,notifications.get(position));
            }
        });
        mViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLikeListener.OnClick(position,notifications.get(position));
            }
        });
    }
}
