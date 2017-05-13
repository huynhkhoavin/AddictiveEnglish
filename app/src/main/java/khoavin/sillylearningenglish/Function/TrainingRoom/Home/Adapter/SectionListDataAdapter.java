package khoavin.sillylearningenglish.Function.TrainingRoom.Home.Adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.Pattern.RecyclerItemClickListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

public class SectionListDataAdapter extends RecycleViewAdapterPattern {
    public SectionListDataAdapter(Context context, ArrayList<Object> dataSource) {
        super(context,dataSource);
    }
    public RecyclerItemClickListener.OnItemClickListener onItemClickListener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_book_home_training, null);
        return new SingleItemRowHolder(v);
    }

    public void setOnItemClickListener(RecyclerItemClickListener.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SingleItemRowHolder mViewHolder = (SingleItemRowHolder) holder;
        final ArrayList<Lesson> lessonItems = ArrayConvert.toArrayList(getDataSource());
        mViewHolder.tvTitle.setText(lessonItems.get(position).getLsTitle());
        Glide.with(getmContext())
                .load(lessonItems.get(position).getLsAvatarUrl())
                .into(mViewHolder.itemImage);
        mViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,position);
            }
        });
        mViewHolder.ratingBar.setRating(Float.parseFloat(lessonItems.get(position).getLsRate()));
    }
}