package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.TrainingRoom.Storage.Storage;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonUnit;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.Service.Constants;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURENT_MEDIA_PLAYER;
import static khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.TrainingHomeConstaint.HomeConstaint.CURRENT_LESSON_UNIT;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class ProgressListAdapter extends RecycleViewAdapterPattern {
    private MediaPlayer mMediaPlayer;
    private int duration = 0;
    private int progressSequence;
    private int currentPlayingSeuquence = -1;
    public ProgressListAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_progress_item,parent,false);
        return new ProgressListViewHolder(itemView);
    }
    public boolean checkIsPlaying(){
        boolean isPlaying = Storage.getInstance().CheckNodeIsExist(CURENT_MEDIA_PLAYER);
        if (isPlaying == true){
            if(((MediaPlayer)Storage.getInstance().getValue(CURENT_MEDIA_PLAYER)).isPlaying()){
                return true;
            };
        }
        return false;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ProgressListViewHolder mViewHolder = (ProgressListViewHolder) holder;
        final ArrayList<LessonUnit> lessonUnits = ArrayConvert.toArrayList(getDataSource());
        mViewHolder.Title.setText(lessonUnits.get(position).getLuName());
        mViewHolder.Duration.setText(lessonUnits.get(position).getLuDuration());

            mViewHolder.gifImageView.setVisibility(View.INVISIBLE);
        if (position == currentPlayingSeuquence){
            mViewHolder.gifImageView.setVisibility(View.VISIBLE);
        }

        if (lessonUnits.get(position).getLuSequence()>progressSequence) {
            mViewHolder.SingleItem.setEnabled(false);
            mViewHolder.SingleItem.setBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
        }
        else if(lessonUnits.get(position).getLuSequence()==progressSequence) {
            mViewHolder.SingleItem.setEnabled(true);
            mViewHolder.SingleItem.setBackgroundColor(getContext().getResources().getColor(R.color.blue_less));
        }
        else {
            mViewHolder.SingleItem.setEnabled(true);
            mViewHolder.SingleItem.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary1));
        }
        int x = lessonUnits.get(0).getCurrentProgressUnit();
        //mViewHolder.progressBar.setEnabled(lessonUnits.get(position).getActiveState());
        if (adapterOnItemClick!=null)
        mViewHolder.SingleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position ,lessonUnits.get(position));
                notifyDataSetChanged();
            }
        });
    }

    public int getProgressSequence() {
        return progressSequence;
    }

    public int getCurrentPlayingSeuquence() {
        return currentPlayingSeuquence;
    }

    public void setCurrentPlayingSeuquence(int currentPlayingSeuquence) {
        this.currentPlayingSeuquence = currentPlayingSeuquence;
        notifyDataSetChanged();
    }

    public void setProgressSequence(int progressSequence) {
        this.progressSequence = progressSequence;
        notifyDataSetChanged();
    }
}
