package khoavin.sillylearningenglish.Function.Profile.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter.SingleViewHolder;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.LessonProgress;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by KhoaVin on 11/07/2017.
 */

public class LessonProgressAdapter extends RecycleViewAdapterPattern {
    /**
     * Initialize
     *
     * @param mContext   The View Context
     * @param dataSource
     */
    public LessonProgressAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_lesson_progress,parent,false);
        return new LessonProgressViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LessonProgressViewHolder lessonProgressViewHolder = (LessonProgressViewHolder)holder;
        ArrayList<LessonProgress> lessonProgresses = ArrayConvert.toArrayList(getDataSource());

        Glide.with(getContext()).load(lessonProgresses.get(position).getLsAvatarUrl()).into(lessonProgressViewHolder.lessonImage);

        lessonProgressViewHolder.tvTitle.setText(lessonProgresses.get(position).getLsTitle());

        float progress = (float)(lessonProgresses.get(position).getLsProgress()/lessonProgresses.get(position).getTotalProgress());

        lessonProgressViewHolder.lessonProgress.setProgress(progress*100);

    }
}
