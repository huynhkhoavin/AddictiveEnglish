package khoavin.sillylearningenglish.Function.Friend.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import khoavin.sillylearningenglish.Pattern.ViewHolderPattern;
import khoavin.sillylearningenglish.R;

/**
 * Created by Khoavin on 3/13/2017.
 */

public class ChatViewHolder extends ViewHolderPattern {
    public ImageView chatAvatar;
    public TextView chatContent;
    public ChatViewHolder(View itemView) {
        super(itemView);
        chatAvatar = (ImageView)itemView.findViewById(R.id.chat_avatar);
        chatContent = (TextView)itemView.findViewById(R.id.chat_content);

    }
}
