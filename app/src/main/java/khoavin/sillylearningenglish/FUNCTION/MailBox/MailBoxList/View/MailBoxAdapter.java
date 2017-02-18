package khoavin.sillylearningenglish.FUNCTION.MailBox.MailBoxList.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import khoavin.sillylearningenglish.PATTERN.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SINGLE_OBJECT.Mail;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class MailBoxAdapter extends RecycleViewAdapterPattern {
    private Mail[] mails;
    public MailBoxAdapter(Context mContext, Object[] dataSource) {
        super(mContext, dataSource);
        mails = (Mail[]) getDataSource();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.single_mail, parent, false);
        return new MailItemViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MailItemViewHolder mViewHolder = (MailItemViewHolder) holder;
        mViewHolder.checkBox.setChecked(mails[position].isChecked());
        mViewHolder.sender.setText(mails[position].getSender());
        mViewHolder.briefContent.setText(mails[position].getBriefContent());
        if (mails[position].isReadStatus()==true){
            mViewHolder.read_status.setImageResource(R.drawable.boxed_mail);
            mViewHolder.singleMail.setBackgroundColor(Color.GRAY);
        }
        else
            mViewHolder.read_status.setImageResource(R.drawable.unbox_mail);
        mViewHolder.singleMail.setBackgroundColor(Color.WHITE);
        }
}
