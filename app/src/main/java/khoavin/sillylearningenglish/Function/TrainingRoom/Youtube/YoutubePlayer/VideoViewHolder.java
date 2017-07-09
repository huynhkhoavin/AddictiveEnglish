package khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.YoutubePlayer;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 08/07/2017.
 */

public class VideoViewHolder extends ViewHolderPattern {

    @BindView(R.id.single_item)
    RelativeLayout singleItem;

    @BindView(R.id.videoImage)
    ImageView videoImage;

    @BindView(R.id.videoTitle)
    TextView videoTitle;

    @BindView(R.id.channelName)
    TextView channelName;

    public VideoViewHolder(View itemView) {
        super(itemView);
    }
}
