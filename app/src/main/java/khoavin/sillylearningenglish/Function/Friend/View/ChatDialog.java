package khoavin.sillylearningenglish.Function.Friend.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.ChatListener;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.SimpleDividerItemDecoration;
import khoavin.sillylearningenglish.SingleViewObject.ChatItem;
import khoavin.sillylearningenglish.SingleViewObject.Friend;

/**
 * Created by Khoavin on 3/12/2017.
 */

public class ChatDialog extends Dialog {
    @BindView(R.id.chatRecycleView) RecyclerView chatRecycleView;
    @BindView(R.id.chatField) EditText chatField;
    @BindView(R.id.btnSend) ImageButton btnSend;
    private ChatListener chatListener;
    private ChatAdapter chatAdapter;
    public ChatDialog(@NonNull Context context) {
        super(context);

    }

    public ChatDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ChatDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_dialog);
        ButterKnife.bind(this);
        setTitle("Huỳnh Khoa Vin");
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chatField.getText().equals(""))
                chatListener.SendMessage(chatField.getText().toString());
            }
        });
        showListChat();
    }
    public void SetChatListener(ChatListener chatListener){
        this.chatListener = chatListener;
    }
    public void showListChat(){
        ArrayList<ChatItem> chatItems = new ArrayList<ChatItem>();
        chatItems.add(new ChatItem("https://lh6.googleusercontent.com/-xt7Q6VwbVkE/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV0pokJsrFTWYM0mwqFv510J5dkC1w/s96-c/photo.jpg","alo"));
        chatItems.add(new ChatItem("https://lh6.googleusercontent.com/-xt7Q6VwbVkE/AAAAAAAAAAI/AAAAAAAAAAA/AAomvV0pokJsrFTWYM0mwqFv510J5dkC1w/s96-c/photo.jpg","alo"));
        chatAdapter = new ChatAdapter(getContext(), ArrayConvert.toObjectArray(chatItems));
        chatRecycleView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatRecycleView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        chatRecycleView.addItemDecoration(dividerItemDecoration);

    }
    public void AddChatItem(ChatItem item)
    {
        chatAdapter.AddChatItem(item);
        chatRecycleView.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                chatRecycleView.smoothScrollToPosition(chatAdapter.getItemCount()-1);
            }
        });
    }
}
