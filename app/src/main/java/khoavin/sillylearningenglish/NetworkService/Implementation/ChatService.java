package khoavin.sillylearningenglish.NetworkService.Implementation;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import khoavin.sillylearningenglish.EventListener.SingleEvent.GetMessageListener;
import khoavin.sillylearningenglish.EventListener.SingleEvent.SendMessageListener;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseChat;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseConstant;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IChatService;

public class ChatService implements IChatService{
    final String TAG = "Chat Service";
    private DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstant.ARG_CHAT_ROOMS);
    private FirebaseUser Current_User = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference getMessageRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstant.ARG_CHAT_ROOMS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    ValueEventListener valueEventListener;
    @Override
    public void sendMessageToUid(String senderUid, String receiverUid, String message, SendMessageListener sendMessageListener){
        try {
            FirebaseChat firebaseChat = new FirebaseChat(message,false);
            ChatRef.child(receiverUid).child(senderUid).push().setValue(firebaseChat);
            sendMessageListener.OnSendSuccess(message);
        }
        catch (Exception e){
            sendMessageListener.OnSendFailed();
        }
    }
    @Override
    public void getMessageFromUid(String Uid, final GetMessageListener getMessageListener){
        try {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<String> listMessage = new ArrayList<String>();
                    ArrayList<String> listKey = new ArrayList<String>();
                    FirebaseChat firebaseChat = new FirebaseChat();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        firebaseChat = data.getValue(FirebaseChat.class);
                        if (firebaseChat.isRead() == false)
                        {
                            listMessage.add(firebaseChat.getMessage());
                            listKey.add(data.getKey());
                            //ChatRef.child(data.getKey()).child("read").setValue(true);
                        }
                    }
                    getMessageListener.OnSuccess(listMessage,listKey);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            getMessageRef.child(Uid).addValueEventListener(valueEventListener);
        }
        catch (Exception e)
        {
            getMessageListener.OnError(e.getMessage());
        }
    }
    public void removeListener(String Uid)
    {
        if (valueEventListener!=null)
        getMessageRef.child(Uid).removeEventListener(valueEventListener);
    }
    public void setReadMessageFromUid(String Uid,String ChatKey)
    {
        try {
            final DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstant.ARG_CHAT_ROOMS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Uid);
            ChatRef.child(ChatKey).child(FirebaseConstant.ARG_READ).setValue(true);
            ChatRef.removeValue();
        }
        catch (Exception e)
        {

        }
    }
}