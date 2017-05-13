package khoavin.sillylearningenglish.Function.Friend.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SingleViewObject.ChatItem;

/**
 * Created by Khoavin on 3/13/2017.
 */

public class ChatAdapter extends RecycleViewAdapterPattern {
    public ChatAdapter(Context mContext, ArrayList<Object> dataSource)
    {
        super(mContext,dataSource);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.single_chat_left,parent,false);
        return new ChatViewHolder(itemView);
    }
    public void AddChatItem(ChatItem chatItem){
        addDataSource(chatItem);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatViewHolder mViewHolder = (ChatViewHolder)holder;
        final ArrayList<ChatItem> chatItems = ArrayConvert.toArrayList(getDataSource());
        if (chatItems.size()==0)
        {
            mViewHolder.chatAvatar.setVisibility(View.INVISIBLE);
            mViewHolder.chatContent.setVisibility(View.INVISIBLE);
            return;
        }
        mViewHolder.chatAvatar.setImageBitmap(chatItems.get(position).getAvatarBitMap());
        mViewHolder.chatContent.setText(chatItems.get(position).getChatContent());
    }
}
