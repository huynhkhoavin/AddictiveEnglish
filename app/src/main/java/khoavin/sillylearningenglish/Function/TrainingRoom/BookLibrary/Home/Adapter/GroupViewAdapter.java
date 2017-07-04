package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter;

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

import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Listener.ItemClickPosition;
import khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Model.GroupItem;
import khoavin.sillylearningenglish.NetworkService.NetworkModels.Lesson;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.Pattern.RecyclerItemClickListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

public class GroupViewAdapter extends RecycleViewAdapterPattern {

    public GroupViewAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }

    public RecyclerItemClickListener.OnItemClickListener  btnMoreClickListener;

    public void setBtnMoreClickListener(RecyclerItemClickListener.OnItemClickListener btnMoreClickListener) {
        this.btnMoreClickListener = btnMoreClickListener;
    }

    public ItemClickPosition itemClickPosition;

    public void setItemClickPosition(ItemClickPosition itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_section_home_training,viewGroup,false);
        return new GroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GroupViewHolder mViewHolder = (GroupViewHolder)holder;
        final ArrayList<GroupItem> lessonCategories = ArrayConvert.toArrayList(getDataSource());
        final String sectionName = lessonCategories.get(position).getHeaderTitle();

        ArrayList singleSectionItems = lessonCategories.get(position).getAllItemsInSection();

        mViewHolder.itemTitle.setText(sectionName);

        SingleViewAdapter itemListDataAdapter = new SingleViewAdapter(getContext(), singleSectionItems);
        itemListDataAdapter.setOnItemClickListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                itemClickPosition.OnClick(position,pos);
            }
        });

        //mViewHolder.recycler_view_list.setHasFixedSize(true);
        mViewHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mViewHolder.recycler_view_list.setAdapter(itemListDataAdapter);


        //mViewHolder.recycler_view_list.setNestedScrollingEnabled(false);

        mViewHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMoreClickListener.onItemClick(v,position);
            }
        });
    }
    public Lesson getItem(int row, int column){
        final ArrayList<GroupItem> lessonCategories = ArrayConvert.toArrayList(getDataSource());
        final Lesson lessonItem = lessonCategories.get(row).getAllItemsInSection().get(column);
        return lessonItem;
    }
}