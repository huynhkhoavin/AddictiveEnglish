package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class ProgressListViewHolder extends ViewHolderPattern {
    public CardView SingleItem;
    public ImageView PlayStatus;
    public TextView Title;
    public TextView Duration;
    public CheckBox Done;
    public ProgressListViewHolder(View itemView) {
        super(itemView);
        PlayStatus = (ImageView)itemView.findViewById(R.id.img_play_status);
        Title = (TextView)itemView.findViewById(R.id.tv_title);
        Duration = (TextView)itemView.findViewById(R.id.tv_duration);
        Done = (CheckBox) itemView.findViewById(R.id.cb_done);
        SingleItem = (CardView)itemView.findViewById(R.id.itemLayout);
    }
}
