package khoavin.sillylearningenglish.Function.Friend.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SingleViewObject.ChatItem;
import khoavin.sillylearningenglish.SingleViewObject.Friend;

/**
 * Created by Khoavin on 3/14/2017.
 */

public class ChatDialogFragment extends DialogFragment {

    @BindView(R.id.chatRecycleView)
    RecyclerView chatRecycleView;
    @BindView(R.id.chatField)
    EditText chatField;
    @BindView(R.id.btnSend)
    ImageButton btnSend;
    private FriendListAdapter chatAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.chat_dialog,container);
        ButterKnife.bind(this,v);
        ArrayList<ChatItem> chatItems = new ArrayList<ChatItem>();
        chatItems.add(new ChatItem("https://lh6.googleusercontent.com/-xt7Q6VwbVkE/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV0pokJsrFTWYM0mwqFv510J5dkC1w/s96-c/photo.jpg","alo"));
        chatItems.add(new ChatItem("https://lh6.googleusercontent.com/-xt7Q6VwbVkE/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV0pokJsrFTWYM0mwqFv510J5dkC1w/s96-c/photo.jpg","alo"));
        ArrayList<Friend> friends = new ArrayList<>();
        friends.add(new Friend("https://lh6.googleusercontent.com/-xt7Q6VwbVkE/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV0pokJsrFTWYM0mwqFv510J5dkC1w/s96-c/photo.jpg","vin huỳnh",true));
        chatAdapter = new FriendListAdapter(getActivity().getApplicationContext(), ArrayConvert.toObjectArray(friends));
        chatRecycleView.setAdapter(chatAdapter);
        return v;
    }
}
