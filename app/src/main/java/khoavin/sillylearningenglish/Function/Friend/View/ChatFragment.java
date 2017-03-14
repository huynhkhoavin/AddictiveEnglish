package khoavin.sillylearningenglish.Function.Friend.View;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.ChatListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SingleViewObject.Friend;

/**
 * Created by Khoavin on 3/13/2017.
 */

public class ChatFragment extends DialogFragment {
    @BindView(R.id.chatRecycleView)
    RecyclerView chatRecycleView;
    @BindView(R.id.chatField)
    EditText chatField;
    @BindView(R.id.btnSend)
    ImageButton btnSend;
    private ChatListener chatListener;
    private FriendListAdapter chatAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.chat_dialog, container, false);
        ButterKnife.bind(this,v);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chatField.getText().equals(""))
                    chatListener.SendMessage(chatField.getText().toString());
            }
        });
//        ArrayList<ChatItem> chatItems = new ArrayList<ChatItem>();
//        chatItems.add(new ChatItem("https://lh6.googleusercontent.com/-xt7Q6VwbVkE/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV0pokJsrFTWYM0mwqFv510J5dkC1w/s96-c/photo.jpg","alo"));
//        chatItems.add(new ChatItem("https://lh6.googleusercontent.com/-xt7Q6VwbVkE/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV0pokJsrFTWYM0mwqFv510J5dkC1w/s96-c/photo.jpg","alo"));
        ArrayList<Friend> friends = new ArrayList<>();
        friends.add(new Friend("https://lh6.googleusercontent.com/-xt7Q6VwbVkE/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV0pokJsrFTWYM0mwqFv510J5dkC1w/s96-c/photo.jpg","vin huỳnh",true));
        chatAdapter = new FriendListAdapter(getActivity(), ArrayConvert.toObjectArray(friends));
        chatRecycleView.setAdapter(chatAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
