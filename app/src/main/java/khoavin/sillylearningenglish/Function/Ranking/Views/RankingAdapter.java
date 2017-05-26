package khoavin.sillylearningenglish.Function.Ranking.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Ranking;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 5/24/2017.
 */

public class RankingAdapter extends RecycleViewAdapterPattern {

    /**
     * Initialize the data source and context.
     *
     * @param context    The context.
     * @param dataSource The data source
     */
    public RankingAdapter(Context context, ArrayList<Object> dataSource) {
        super(context, dataSource);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_rank, parent, false);
        return new RankingItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ArrayList<Ranking> rankings = ArrayConvert.toArrayList(getDataSource());
        RankingItemViewHolder mViewHolder = (RankingItemViewHolder) holder;

        mViewHolder.singleRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position, rankings.get(position));
            }
        });
        mViewHolder.userRank.setText(Common.getUserRankPositionText(rankings.get(position).getRankPosition()));
        mViewHolder.userName.setText(rankings.get(position).getUserName());
        mViewHolder.winRate.setText(String.format(getContext().getResources().getString(R.string.ranking_win_rate_title), Common.GetWinRate(rankings.get(position).getTotalBattle(), rankings.get(position).getWinBattle())));
        mViewHolder.totalBattle.setText(String.format(getContext().getResources().getString(R.string.ranking_total_battle_title), Common.FormatBigNumber(rankings.get(position).getTotalBattle())));
        mViewHolder.medalTitle.setText(Common.GetMedalTitleFromLevel(rankings.get(position).getLevel(), getContext()));
        mViewHolder.medalImage.setImageResource(Common.getRankMedalImage(rankings.get(position).getLevel()));
        Glide.with(getContext())
                .load(rankings.get(position).getUserAvatar())
                .into(mViewHolder.userAvatar);

    }
}
