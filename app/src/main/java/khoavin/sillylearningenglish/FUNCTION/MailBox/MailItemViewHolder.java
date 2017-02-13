package khoavin.sillylearningenglish.FUNCTION.MailBox;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import khoavin.sillylearningenglish.PATTERN.ViewHolderPattern;
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

    public MailItemViewHolder(View itemView) {
        super(itemView);
        checkBox = (CheckBox)itemView.findViewById(R.id.checkbox);
        singleMail = (LinearLayout)itemView.findViewById(R.id.single_mail);
        sender = (TextView) itemView.findViewById(R.id.sender);
        briefContent = (TextView)itemView.findViewById(R.id.mail_content);
        read_status = (ImageView)itemView.findViewById(R.id.read_status);
    }
}
