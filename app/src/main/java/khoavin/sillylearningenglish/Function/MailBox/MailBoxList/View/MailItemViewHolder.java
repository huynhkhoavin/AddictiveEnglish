package khoavin.sillylearningenglish.Function.MailBox.MailBoxList.View;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class MailItemViewHolder extends ViewHolderPattern {
    public LinearLayout singleMail;
    public CheckBox checkBox;
    public TextView sender;
    public TextView briefContent;
    public ImageView read_status;
    public TextView mail_title;
    public TextView sent_time;

    public MailItemViewHolder(View itemView) {
        super(itemView);
        checkBox = (CheckBox)itemView.findViewById(R.id.mail_checkbox);
        singleMail = (LinearLayout)itemView.findViewById(R.id.single_mail);
        sender = (TextView) itemView.findViewById(R.id.mail_sender);
        briefContent = (TextView)itemView.findViewById(R.id.mail_content);
        read_status = (ImageView)itemView.findViewById(R.id.mail_red_dot);
        mail_title =  (TextView) itemView.findViewById(R.id.mail_title);
        sent_time =  (TextView) itemView.findViewById(R.id.mail_time);
    }
}
