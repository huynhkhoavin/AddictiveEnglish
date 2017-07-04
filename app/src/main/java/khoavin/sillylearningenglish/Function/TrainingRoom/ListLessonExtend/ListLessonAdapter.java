package khoavin.sillylearningenglish.Function.TrainingRoom.ListLessonExtend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter.SingleViewHolder;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.Pattern.RecyclerItemClickListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by KhoaVin on 04/07/2017.
 */

public class ListLessonAdapter extends RecycleViewAdapterPattern {
    /**
     * Initialize
     *
     * @param mContext   The View Context
     * @param dataSource
     */
    public ListLessonAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    public RecyclerItemClickListener.OnItemClickListener onItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_book,parent,false);
        return new SingleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SingleViewHolder mViewHolder = (SingleViewHolder) holder;
        final ArrayList<Lesson> lessonItems = ArrayConvert.toArrayList(getDataSource());
        mViewHolder.tvTitle.setText(lessonItems.get(position).getLsTitle());
        mViewHolder.tvAuthor.setText(lessonItems.get(position).getLsAuthor());
        Glide.with(getContext())
                .load(lessonItems.get(position).getLsAvatarUrl())
                .into(mViewHolder.itemImage);
        mViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position,lessonItems.get(position));
            }
        });

        mViewHolder.ratingBar.setRating(lessonItems.get(position).getRate());
    }
}
