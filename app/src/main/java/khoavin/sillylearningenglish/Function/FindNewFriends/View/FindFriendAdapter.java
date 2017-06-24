package khoavin.sillylearningenglish.Function.FindNewFriends.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.FindNewFriends.Object.FindFriendItem;
import khoavin.sillylearningenglish.Function.Friend.View.FriendListViewHolder;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by KhoaVin on 22/06/2017.
 */

public class FindFriendAdapter extends RecycleViewAdapterPattern {
    /**
     * Initialize
     *
     * @param mContext   The View Context
     * @param dataSource
     */
    public FindFriendAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_add_friends_item, parent, false);
        return new FindFriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FindFriendViewHolder mViewHolder = (FindFriendViewHolder)holder;
        final ArrayList<FindFriendItem> arrayList = ArrayConvert.toArrayList(getDataSource());

        Glide.with(getContext())
                .load(arrayList.get(position).getAvatarUrl())
                .placeholder(R.drawable.avatar_holder)
                .into(mViewHolder.friendAvatar);
        mViewHolder.friendName.setText(arrayList.get(position).getName());
        mViewHolder.btnAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position,arrayList.get(position));
            }
        });
    }
}
