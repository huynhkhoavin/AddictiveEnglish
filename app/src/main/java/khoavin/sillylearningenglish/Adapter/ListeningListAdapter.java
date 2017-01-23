package khoavin.sillylearningenglish.Adapter;

import android.content.Context;

import khoavin.sillylearningenglish.EntityDatabase.Silly_english.ListeningUnit;
import khoavin.sillylearningenglish.ToolFactory.IViewHolder;

/**
 * Created by KhoaVin on 1/22/2017.
 */

public class ListeningListAdapter extends AdapterPattern<ListeningUnit> implements IViewHolder {
    public ListeningListAdapter(Context c, ListeningUnit[] dataSource, IViewHolder viewHolder) {
        super(c, dataSource, viewHolder);
    }
}
