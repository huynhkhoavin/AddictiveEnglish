package khoavin.sillylearningenglish.Function.FindNewFriends.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 22/06/2017.
 */

public class FindFriendViewHolder extends ViewHolderPattern {

    @BindView(R.id.avatar)
    ImageView friendAvatar;
    @BindView(R.id.name)
    TextView friendName;
    @BindView(R.id.btn_AddFriend)
    LinearLayout btnAddFriends;
    public FindFriendViewHolder(View itemView) {
        super(itemView);
    }
}
