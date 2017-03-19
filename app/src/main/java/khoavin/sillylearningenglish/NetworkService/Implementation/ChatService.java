package khoavin.sillylearningenglish.NetworkService.Implementation;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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

    @Override
    public void sendMessageToUid(String senderUid, String receiverUid, String message, SendMessageListener sendMessageListener){
        try {
            FirebaseChat firebaseChat = new FirebaseChat(message,false);
            ChatRef.child(receiverUid).child(senderUid).push().setValue(firebaseChat);
            sendMessageListener.OnSendSuccess();
        }
        catch (Exception e){
            sendMessageListener.OnSendFailed();
        }
    }
    @Override
    public void getMessageFromUid(String Uid, final GetMessageListener getMessageListener){
        try {
            final DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstant.ARG_CHAT_ROOMS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Uid);

            ChatRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<String> listMessage = new ArrayList<String>();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        listMessage.add(data.child(FirebaseConstant.ARG_MESSAGE).getValue(String.class));
                    }
                    //ChatRef.removeValue();
                    getMessageListener.OnSuccess(listMessage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {
            getMessageListener.OnError(e.getMessage());
        }
    }
}