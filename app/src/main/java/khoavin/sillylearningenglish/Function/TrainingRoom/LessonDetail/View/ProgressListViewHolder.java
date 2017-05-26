package khoavin.sillylearningenglish.Function.TrainingRoom.LessonDetail.View;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class ProgressListViewHolder extends ViewHolderPattern {
    public RelativeLayout SingleItem;
    public TextView Title;
    public TextView Duration;
    public ProgressListViewHolder(View itemView) {
        super(itemView);
        SingleItem = (RelativeLayout)itemView.findViewById(R.id.itemLayout) ;
        Title = (TextView)itemView.findViewById(R.id.tv_title);
        Duration = (TextView)itemView.findViewById(R.id.tv_duration);
    }
}
