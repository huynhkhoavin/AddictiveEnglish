package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SingleObject.Mail;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArrayList<Mail> mails = ArrayConvert.toArrayList(getDataSource());
        MailItemViewHolder mViewHolder = (MailItemViewHolder) holder;
        mViewHolder.checkBox.setChecked(mails.get(position).isChecked());
        mViewHolder.sender.setText(mails.get(position).getSender());
        mViewHolder.briefContent.setText(mails.get(position).getBriefContent());
        if (mails.get(position).isReadStatus()==true){
            mViewHolder.read_status.setImageResource(R.drawable.boxed_mail);
            mViewHolder.singleMail.setBackgroundColor(Color.GRAY);
        }
        else
            mViewHolder.read_status.setImageResource(R.drawable.unbox_mail);
            mViewHolder.singleMail.setBackgroundColor(Color.WHITE);
        }
}
