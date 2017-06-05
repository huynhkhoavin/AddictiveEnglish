package khoavin.sillylearningenglish.Function.Friend.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.FriendActionListener;
import khoavin.sillylearningenglish.Pattern.RecycleViewAdapterPattern;
import khoavin.sillylearningenglish.R;
import khoavin.sillylearningenglish.SYSTEM.ToolFactory.ArrayConvert;
import khoavin.sillylearningenglish.SingleViewObject.Friend;


/**
 * Created by KhoaVin on 2/12/2017.
 */

public class FriendListAdapter extends RecycleViewAdapterPattern {

    FriendActionListener friendActionListener;

    public FriendActionListener getFriendActionListener() {
        return friendActionListener;
    }

    public void setFriendActionListener(FriendActionListener friendActionListener) {
        this.friendActionListener = friendActionListener;
    }

    public FriendListAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.single_friend, parent, false);
        return new FriendListViewHolder(itemView);
    }
    public void UpdateDataSource(ArrayList<Object> friends){
        setDataSource(friends);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FriendListViewHolder mViewHolder = (FriendListViewHolder) holder;
        final ArrayList<Friend> friends = ArrayConvert.toArrayList(getDataSource());
        Glide.with(getContext())
                .load(friends.get(position).getAvatar())
                .placeholder(R.drawable.avatar_holder)
                .into(mViewHolder.avatar);
        mViewHolder.singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.one) {
                            Toast.makeText(v.getContext(), "Popup : " + position, Toast.LENGTH_SHORT).show();
                            ArrayList<Friend> listFriend = ArrayConvert.toArrayList(getDataSource());
                            if(listFriend.get(position).isHave_New_Message())
                            {
                                listFriend.get(position).setHave_New_Message(false);
                                notifyDataSetChanged();
                            }
                            friendActionListener.ChatAction(position,(Friend)getDataSource().get(position));
                        }
                        else if(item.getItemId() == R.id.two){
                            friendActionListener.GetInfoAction(position,(Friend)getDataSource().get(position));
                        }
                        else if(item.getItemId() == R.id.three){
                            friendActionListener.ChallengeAction(position,(Friend)getDataSource().get(position));
                        }
                        else if(item.getItemId() == R.id.four){
                            friendActionListener.UnFriend(position,(Friend)getDataSource().get(position));
                        }
                        return true;
                    }
                });
                popupMenu.show();//showing popup menu
            }
        });
        mViewHolder.name.setText(friends.get(position).getName());
        if (friends.get(position).getOnline_Status()==true){
            mViewHolder.online_status.setVisibility(View.VISIBLE);
        }
        else if(friends.get(position).getOnline_Status()==false){
            mViewHolder.online_status.setVisibility(View.INVISIBLE);
        }
        if(friends.get(position).isHave_New_Message()){
            mViewHolder.message_ic.setVisibility(View.VISIBLE);
        }
        else
        {
            mViewHolder.message_ic.setVisibility(View.INVISIBLE);
        }
    }
    public void UpdateItem(String Uid, Friend newFriend){
        ArrayList<Friend> listFriend = ArrayConvert.toArrayList(getDataSource());
        for (int i = 0; i<listFriend.size(); i ++){
            if (Uid == listFriend.get(i).getUid())
            {
                this.updateDataSource(i,newFriend);
            }
        }
    }
    public int SearchPosition(String Uid){
        ArrayList<Friend> listFriend = ArrayConvert.toArrayList(getDataSource());
        for (int i = 0; i<listFriend.size(); i ++){
            if (Uid.equals(listFriend.get(i).getUid()))
            {
                return i;
            }
        }
        return -1;
    }
    public boolean checkUidSnoozing(String Uid){
        ArrayList<Friend> listFriend = ArrayConvert.toArrayList(getDataSource());
        for (int i = 0; i<listFriend.size(); i ++){
            if (Uid.equals(listFriend.get(i).getUid()))
            {
                return listFriend.get(i).isHave_New_Message();
            }
        }
        return false;
    }
}
