package khoavin.sillylearningenglish.Function.Profile.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import butterknife.BindView;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 11/07/2017.
 */

public class LessonProgressViewHolder extends ViewHolderPattern {
    @BindView(R.id.lessonImage)
    ImageView lessonImage;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lessonProgress)
    RoundCornerProgressBar lessonProgress;

    public LessonProgressViewHolder(View itemView) {
        super(itemView);
    }
}
