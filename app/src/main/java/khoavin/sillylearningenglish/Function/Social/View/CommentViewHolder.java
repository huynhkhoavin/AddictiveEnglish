package khoavin.sillylearningenglish.Function.Social.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 02/06/2017.
 */

public class CommentViewHolder extends ViewHolderPattern {
    @BindView(R.id.img_user_avatar)
    ImageView userAvatar;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvDuration)
    TextView tvDuration;
    @BindView(R.id.tvContent)
    TextView tvContent;
    public CommentViewHolder(View itemView) {
        super(itemView);
    }
}
