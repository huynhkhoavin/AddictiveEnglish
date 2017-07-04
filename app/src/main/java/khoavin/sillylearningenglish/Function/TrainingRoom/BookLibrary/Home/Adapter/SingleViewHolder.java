package khoavin.sillylearningenglish.Function.TrainingRoom.BookLibrary.Home.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by Khoavin on 4/2/2017.
 */

public class SingleViewHolder extends ViewHolderPattern {
    @BindView(R.id.tvTitle)
    public TextView tvTitle;
    @BindView(R.id.itemImage)
    public ImageView itemImage;
    @BindView(R.id.rating)
    public RatingBar ratingBar;
    @BindView(R.id.tvAuthor)
    public TextView tvAuthor;
    @BindView(R.id.lesson_layout)
    public RelativeLayout relativeLayout;
    public SingleViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}