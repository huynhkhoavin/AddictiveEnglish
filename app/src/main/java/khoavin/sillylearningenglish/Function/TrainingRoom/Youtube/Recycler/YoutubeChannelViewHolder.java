package khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 08/07/2017.
 */

public class YoutubeChannelViewHolder extends ViewHolderPattern {

    @BindView(R.id.channelImage)
    ImageView channelImage;

    @BindView(R.id.channelName)
    TextView channelName;

    public YoutubeChannelViewHolder(View itemView) {
        super(itemView);
    }
}
