package khoavin.sillylearningenglish.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import khoavin.sillylearningenglish.R;

/**
 * Created by KhoaVin on 2/12/2017.
 */

public class FriendListItemViewHolder extends ViewHolder {
    public ImageView avatar;
    public TextView name;
    public ImageView online_status;

    public FriendListItemViewHolder(View itemView) {
        super(itemView);
        avatar = (ImageView)itemView.findViewById(R.id.avatar);
        name = (TextView)itemView.findViewById(R.id.name);
        online_status = (ImageView)itemView.findViewById(R.id.online_status);
    }
}
