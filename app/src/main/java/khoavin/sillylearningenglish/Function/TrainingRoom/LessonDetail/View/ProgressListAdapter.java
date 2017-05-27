package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURENT_MEDIA_PLAYER;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class ProgressListAdapter extends RecycleViewAdapterPattern {
    private MediaPlayer mMediaPlayer;
    private int duration = 0;

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
        mViewHolder.Duration.setText(lessonUnits.get(position).getLuDuration());
        if (lessonUnits.get(position).getLuId()>(lessonUnits.get(0).getCurrentProgressUnit()+1))
        {
            mViewHolder.SingleItem.setEnabled(false);
        }
        int x = lessonUnits.get(0).getCurrentProgressUnit();
        //mViewHolder.progressBar.setEnabled(lessonUnits.get(position).getActiveState());
        if (adapterOnItemClick!=null)
        mViewHolder.SingleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position ,lessonUnits.get(position));
            }
        });
    }
}
