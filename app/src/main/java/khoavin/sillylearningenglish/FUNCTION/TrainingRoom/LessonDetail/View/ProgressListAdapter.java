package khoavin.sillylearningenglish.FUNCTION.TrainingRoom.LessonDetail.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.PATTERN.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.ProgressUnit;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class ProgressListAdapter extends RecycleViewAdapterPattern {
    public ProgressListAdapter(Context mContext, Object[] dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_progress_item,parent,false);
        return new ProgressListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProgressListViewHolder mViewHolder = (ProgressListViewHolder) holder;
        ProgressUnit[] progressUnits = (ProgressUnit[])getDataSource();
        switch (progressUnits[position].getPlayStatus())
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
        mViewHolder.Title.setText(progressUnits[position].getTitle());
        mViewHolder.Duration.setText(progressUnits[position].getDuration());
        mViewHolder.Done.setChecked(progressUnits[position].isDone());
    }
}
