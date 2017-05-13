package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SingleViewObject.ProgressUnit;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class ProgressListAdapter extends RecycleViewAdapterPattern {
    public ProgressListAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_progress_item,parent,false);
        return new ProgressListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ProgressListViewHolder mViewHolder = (ProgressListViewHolder) holder;
        final ArrayList<ProgressUnit> progressUnits = ArrayConvert.toArrayList(getDataSource());
        switch (progressUnits.get(position).getPlayStatus())
        {
            //nothing
            case 0:
            {
                mViewHolder.PlayStatus.setVisibility(View.INVISIBLE);
            } break;
            //is playing
            case 1:
            {
                mViewHolder.PlayStatus.setVisibility(View.INVISIBLE);
                mViewHolder.PlayStatus.setBackgroundResource(R.drawable.ic_pause);
            }break;

            //isPause
            case 2:
            {
                mViewHolder.PlayStatus.setVisibility(View.INVISIBLE);
                mViewHolder.PlayStatus.setBackgroundResource(R.drawable.ic_play);
            }break;
        }
        mViewHolder.Title.setText(progressUnits.get(position).getTitle());
        mViewHolder.Duration.setText(progressUnits.get(position).getDuration());
        mViewHolder.Done.setChecked(progressUnits.get(position).isDone());
        if (adapterOnItemClick!=null)
        mViewHolder.SingleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position,progressUnits.get(position));
            }
        });
    }
}
