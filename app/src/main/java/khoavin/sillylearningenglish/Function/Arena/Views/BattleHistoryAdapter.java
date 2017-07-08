package khoavin.sillylearningenglish.Function.Arena.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.BattleHistory;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SingleViewObject.Common;

/**
 * Created by OatOal on 6/20/2017.
 */

public class BattleHistoryAdapter extends RecycleViewAdapterPattern {
    public BattleHistoryAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.single_battle_history, parent, false);
        return new BattleHistoryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ArrayList<BattleHistory> histories = ArrayConvert.toArrayList(getDataSource());
        BattleHistoryItemViewHolder mViewHolder = (BattleHistoryItemViewHolder) holder;


        mViewHolder.singleHistoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position, histories.get(position));
            }
        });

        //mViewHolder.userAvatar
        mViewHolder.userName.setText(histories.get(position).getUserName());
        mViewHolder.userTrueAnswer.setText(String.format(getContext().getResources().getString(R.string.total_true_answer), histories.get(position).getUserTrueAnswer()));
        mViewHolder.userTotalTime.setText(String.format(getContext().getResources().getString(R.string.total_answer_time), Common.GetSillyDateFormat().MillisecondToString(histories.get(position).getUserTotalTime())));
        Glide.with(getContext())
                .load(histories.get(position).getUserAvatar())
                .into(mViewHolder.userAvatar);

        //mViewHolder.enemyAvatar
        mViewHolder.enemyName.setText(histories.get(position).getEnemyName());
        mViewHolder.enemyTrueAnswer.setText(String.format(getContext().getResources().getString(R.string.total_true_answer), histories.get(position).getEnemyTrueAnswer()));
        mViewHolder.enemyTotalTime.setText(String.format(getContext().getResources().getString(R.string.total_answer_time), Common.GetSillyDateFormat().MillisecondToString(histories.get(position).getEnemyTotalTime())));
        Glide.with(getContext())
                .load(histories.get(position).getEnemyAvatar())
                .into(mViewHolder.enemyAvatar);

        if (histories.get(position).isVictoryBattle()) {
            mViewHolder.lostItem.setVisibility(View.GONE);
            mViewHolder.winItem.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.lostItem.setVisibility(View.VISIBLE);
            mViewHolder.winItem.setVisibility(View.GONE);
        }

        mViewHolder.dateCreate.setText(Common.GetSillyDateFormat().FindTotalDateFromNow(histories.get(position).getDateCreate(), getContext()));
    }
}

