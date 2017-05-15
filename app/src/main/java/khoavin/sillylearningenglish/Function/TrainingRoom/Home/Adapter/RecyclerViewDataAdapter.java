package khoavin.sillylearningenglish.Function.TrainingRoom.Home.Adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Listener.ItemClickPosition;
import khoavin.sillylearningenglish.Function.TrainingRoom.Home.Model.SortSession;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.Pattern.RecyclerItemClickListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

public class RecyclerViewDataAdapter extends RecycleViewAdapterPattern {

    public RecyclerViewDataAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }
    public ItemClickPosition itemClickPosition;

    public void setItemClickPosition(ItemClickPosition itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_section_home_training,viewGroup,false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemRowHolder mViewHolder = (ItemRowHolder)holder;
        final ArrayList<SortSession> lessonCategories = ArrayConvert.toArrayList(getDataSource());
        final String sectionName = lessonCategories.get(position).getHeaderTitle();

        ArrayList singleSectionItems = lessonCategories.get(position).getAllItemsInSection();

        mViewHolder.itemTitle.setText(sectionName);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(getContext(), singleSectionItems);
        itemListDataAdapter.setOnItemClickListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                itemClickPosition.OnClick(position,pos);
            }
        });

        mViewHolder.recycler_view_list.setHasFixedSize(true);
        mViewHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mViewHolder.recycler_view_list.setAdapter(itemListDataAdapter);


        mViewHolder.recycler_view_list.setNestedScrollingEnabled(false);

        mViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public Lesson getItem(int row, int column){
        final ArrayList<SortSession> lessonCategories = ArrayConvert.toArrayList(getDataSource());
        final Lesson lessonItem = lessonCategories.get(row).getAllItemsInSection().get(column);
        return lessonItem;
    }
}