package khoavin.sillylearningenglish.Function.Friend.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class FriendListViewHolder extends ViewHolderPattern {
    @BindView(R.id.single_item) LinearLayout singleItem;
    @BindView(R.id.avatar) ImageView avatar;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.online_status) ImageView online_status;
    @BindView(R.id.ms_bg_ic) RelativeLayout message_ic;
    public FriendListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
