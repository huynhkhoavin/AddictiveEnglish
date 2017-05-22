package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.NetworkModels.Inbox;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class MailBoxAdapter extends RecycleViewAdapterPattern {
    public MailBoxAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.single_mail, parent, false);
        return new MailItemViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ArrayList<Inbox> mails = ArrayConvert.toArrayList(getDataSource());
        MailItemViewHolder mViewHolder = (MailItemViewHolder) holder;
        mViewHolder.checkBox.setChecked(false);
        mViewHolder.sender.setText(mails.get(position).getSenderName());
        mViewHolder.briefContent.setText(mails.get(position).getContent());
        mViewHolder.singleMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClick.OnClick(position,mails.get(position));
            }
        });

        mViewHolder.sent_time.setText("3 days ago");
        mViewHolder.mail_title.setText(mails.get(position).getTitle());

        if (mails.get(position).IsRead()){
            mViewHolder.read_status.setImageResource(R.drawable.mail_box_just_unboxed);
            mViewHolder.singleMail.setBackgroundColor(Color.GRAY);
        }
        else
            mViewHolder.read_status.setImageResource(R.drawable.mail_box_not_unboxed);
            mViewHolder.singleMail.setBackgroundColor(Color.WHITE);
        }
}
