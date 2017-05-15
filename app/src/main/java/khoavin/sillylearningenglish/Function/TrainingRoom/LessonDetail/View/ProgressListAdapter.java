package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
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
        final ArrayList<LessonUnit> lessonUnits = ArrayConvert.toArrayList(getDataSource());
        mViewHolder.Title.setText(lessonUnits.get(position).getLuName());
        //mViewHolder.Duration.setText(lessonUnits.get(position).get);
        mViewHolder.Done.setChecked(lessonUnits.get(position).getActiveState());
        if (adapterOnItemClick!=null)
        mViewHolder.SingleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position,lessonUnits.get(position));
            }
        });
    }
}
