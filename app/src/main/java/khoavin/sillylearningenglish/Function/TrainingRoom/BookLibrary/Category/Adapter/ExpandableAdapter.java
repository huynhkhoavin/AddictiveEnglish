package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Category.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/15/2017.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mHeaderGroup;
    private HashMap<String, List<String>> mDataChild;

    public ExpandableAdapter(Context context, List<String> headerGroup, HashMap<String, List<String>> datas) {
        mContext = context;
        mHeaderGroup = headerGroup;
        mDataChild = datas;
    }

    @Override
    public int getGroupCount() {
        return mHeaderGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mHeaderGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.group_layout, parent, false);
        }

        TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_header);
        tvHeader.setText(mHeaderGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.unit_row_layout, null);
        }

        TextView tvStudentName = (TextView) convertView.findViewById(R.id.tv_student_name);
        TextView tvStudentMediumScore = (TextView) convertView.findViewById(R.id.tv_student_medium_score);
        tvStudentName.setText(((String) getChild(groupPosition, childPosition)));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
