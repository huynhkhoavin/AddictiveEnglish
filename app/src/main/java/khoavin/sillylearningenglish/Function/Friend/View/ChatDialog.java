package khoavin.sillylearningenglish.Function.Friend.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import khoavin.sillylearningenglish.EventListener.SingleEvent.ChatListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.GetMessageListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.ListItemAddListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.SendMessageListener;
import khoavin.sillylearningenglish.Function.Friend.ChatObject.ChatRoom;
import khoavin.sillylearningenglish.Function.Friend.ChatObject.ChatUnit;
import khoavin.sillylearningenglish.Function.Friend.ChatObject.ManyChatRoom;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IChatService;
import khoavin.sillylearningenglish.Pattern.ProgressAsyncTask;
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
    private SendMessageListener sendMessageListener;
    private GetMessageListener getMessageListener;

    private Friend currentChatter;

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    Bitmap friendAvatar;
    Bitmap userAvatar;

    @Inject
    ManyChatRoom manyChatRoom;

    @Inject
    IChatService chatService;

    public ChatDialog(Context context) {
        super(context);
        //this.chatService = chatService;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_dialog);
        ButterKnife.bind(this);

        //region REGISTER SEND MESSAGE EVENT
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chatField.getText().equals(""))
                    if (currentChatter!= null)
                    {
                        //Get Message from Chat Field
                        String Message = chatField.getText().toString();
                        chatField.setText("");
                        //Call Chat Service To Send Mesage to Friend
                        chatService.sendMessageToUid(
                                firebaseUser.getUid(),
                                currentChatter.getUid(),
                                Message,
                                sendMessageListener);

                        //Add Chat Unit To Chat Room
                        manyChatRoom.AddChatUnit(new ChatUnit(false,Message));
                    }
            }
        });
        //endregion

        //region REGISTER LISTENER
        registerListener();
    }

    public void GetAvatar(){
        ProgressAsyncTask progressAsynctask = new ProgressAsyncTask(getContext()) {
            @Override
            public void onDoing() {
                try {
                    friendAvatar = Glide.with(getContext())
                            .load(currentChatter.getAvatar())
                            .asBitmap().into(32, 32).get();
                    userAvatar = Glide.with(getContext())
                            .load(firebaseUser.getPhotoUrl())
                            .asBitmap().into(32, 32).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTaskComplete(Void aVoid) {
                showListChat();
            }
        };
        progressAsynctask.execute();

    }
    public void Show(Friend friend)
    {
        super.show();
        this.currentChatter = friend;
        GetAvatar();
        if (chatAdapter != null)
        chatAdapter.ClearDataSource();
        //Add Chat Room to Many Chat Room If not exist
        if (!manyChatRoom.ChatRoomIsExist(friend.getUid()))
        {
            manyChatRoom.AddChatRoom(friend);
        }
        manyChatRoom.setCurrFriendUid(friend.getUid());

    }

    @Override
    public void dismiss() {
        super.dismiss();
        chatService.removeListener(currentChatter.getUid());
    }

    public void GetMessageFromUid(String Uid)
    {
        chatService.getMessageFromUid(Uid,getMessageListener);
    }
    public void registerListener()  {

        manyChatRoom.setListItemAddListener(new ListItemAddListener() {
            @Override
            public void addItemEvent(String Uid,ChatUnit chatUnit) {
                if (Uid.equals(currentChatter.getUid()))
                {
                    ChatItem chatItem;
                    if (chatUnit.isFriend()){
                        chatItem = new ChatItem(friendAvatar,chatUnit.getMesage());
                    }
                    else
                    {
                        chatItem = new ChatItem(userAvatar,chatUnit.getMesage());
                    }
                    AddChatItem(chatItem);
                }
            }
        });
        getMessageListener = new GetMessageListener() {
            @Override
            public void OnSuccess(ArrayList<String> listMessage, ArrayList<String> listKeys) {
                manyChatRoom.AddChatUnits(true,listMessage);
                for (String string : listKeys)
                {
                    chatService.setReadMessageFromUid(currentChatter.getUid(),string);
                }
            }

            @Override
            public void OnError(String Error) {

            }
        };
        sendMessageListener = new SendMessageListener() {
            @Override
            public void OnSendSuccess(String Message) {
                //manyChatRoom.AddChatUnit(new ChatUnit(false,Message));
            }

            @Override
            public void OnSendFailed() {

            }
        };
    }

    public Friend getCurrentChatter() {
        return currentChatter;
    }

    public void showListChat(){
        ChatRoom currChatRoom = manyChatRoom.getListChatRoom().get(manyChatRoom.getCurrFriendUid());
        ArrayList<ChatItem> chatItems = new ArrayList<>();
        for (ChatUnit chatUnit : currChatRoom.getListChatStore())
        {
            if (chatUnit.isFriend())
            {
                chatItems.add(new ChatItem(friendAvatar,chatUnit.getMesage()));
            }
            else
            {
                chatItems.add(new ChatItem(userAvatar,chatUnit.getMesage()));
            }
        }
        chatAdapter = new ChatAdapter(getContext(),ArrayConvert.toObjectArray(chatItems));
        chatRecycleView.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        chatRecycleView.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration dividerItemDecoration = new SimpleDividerItemDecoration(getContext());
        chatRecycleView.addItemDecoration(dividerItemDecoration);
    }
    public void AddChatItem(ChatItem chatItem)
    {
        if (chatAdapter==null)
            return;
        if (!chatField.getText().equals(""))
        chatAdapter.AddChatItem(chatItem);
        chatField.setText("");
        chatRecycleView.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                chatRecycleView.smoothScrollToPosition(chatAdapter.getItemCount()-1);
            }
        });
    }
}
