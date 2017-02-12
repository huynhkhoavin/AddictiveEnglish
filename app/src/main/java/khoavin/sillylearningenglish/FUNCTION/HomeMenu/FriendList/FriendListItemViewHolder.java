package khoavin.sillylearningenglish.FUNCTION.HomeMenu.FriendList;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.PATTERN.ViewHolderPattern;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class FriendListItemViewHolder extends ViewHolderPattern {
    public LinearLayout singleItem;
    public ImageView avatar;
    public TextView name;
    public ImageView online_status;

    public FriendListItemViewHolder(View itemView) {
        super(itemView);
        singleItem = (LinearLayout)itemView.findViewById(R.id.single_item);
        avatar = (ImageView)itemView.findViewById(R.id.avatar);
        name = (TextView)itemView.findViewById(R.id.name);
        online_status = (ImageView)itemView.findViewById(R.id.online_status);
    }
}
