package khoavin.sillylearningenglish.Function.TrainingRoom.Home.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class SingleItemRowHolder extends ViewHolderPattern {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.itemImage)
    ImageView itemImage;
    @BindView(R.id.rating)
    RatingBar ratingBar;
    @BindView(R.id.lesson_layout)
    LinearLayout linearLayout;
    public SingleItemRowHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}