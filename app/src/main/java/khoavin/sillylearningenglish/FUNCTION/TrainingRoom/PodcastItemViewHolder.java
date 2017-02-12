package khoavin.sillylearningenglish.FUNCTION.TrainingRoom;

import android.view.View;
import android.widget.TextView;

import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.PATTERN.ViewHolderPattern;

/**
 * Created by KhoaVin on 1/24/2017.
 */

public class PodcastItemViewHolder extends ViewHolderPattern {

    public TextView level;
    public TextView title;
    public TextView source;
    public TextView chapter;
    public TextView exp;

    public PodcastItemViewHolder(View itemView) {
        super(itemView);
        level = (TextView) itemView.findViewById(R.id.podcast_level);
        title= (TextView)itemView.findViewById(R.id.podcast_title);
        source = (TextView)itemView.findViewById(R.id.podcast_source);
        chapter = (TextView)itemView.findViewById(R.id.podcast_chapter);
        exp = (TextView) itemView.findViewById(R.id.podcast_exp);
    }
}
