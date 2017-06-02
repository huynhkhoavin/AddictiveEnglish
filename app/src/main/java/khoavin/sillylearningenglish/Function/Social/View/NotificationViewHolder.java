package khoavin.sillylearningenglish.Function.Social.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 29/05/2017.
 */

public class NotificationViewHolder extends ViewHolderPattern {
    @BindView(R.id.img_user_avatar)ImageView userAvatar;
    @BindView(R.id.tv_user_name) TextView userName;
    @BindView(R.id.tv_user_location)  TextView userLocation;
    @BindView(R.id.post_time)  TextView postTime;
    @BindView(R.id.tv_notify_content)  TextView notifyContent;
    @BindView(R.id.tv_likeCount) TextView tvLikeCount;
    @BindView(R.id.btn_like) ImageView btnLike;
    @BindView(R.id.tv_CommentCount) TextView commentCount;
    @BindView(R.id.btn_comment) ImageView btnComment;
    @BindView(R.id.singleItem)
    RelativeLayout itemLayout;
    public NotificationViewHolder(View itemView) {
        super(itemView);
    }
}
