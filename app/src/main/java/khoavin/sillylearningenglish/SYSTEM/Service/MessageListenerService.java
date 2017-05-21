package khoavin.sillylearningenglish.SYSTEM.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseChat;
import khoavin.sillylearningenglish.FirebaseObject.FirebaseConstant;

/**
 * Created by Khoavin on 3/18/2017.
 */

public class MessageListenerService extends Service {
    DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child(FirebaseConstant.ARG_CHAT_ROOMS).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    final String TAG = getClass().getName().toString();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "FirstService started");


        ChatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                FirebaseChat firebaseChat;
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    firebaseChat = data.getValue(FirebaseChat.class);
                    if (firebaseChat.isRead()){
                        ChatRef.child(data.getKey()).removeValue();
                    }
                    else
                    {
                        HashMap<String,String> msg = new HashMap<String, String>();
                        msg.put("UID",dataSnapshot.getKey());
                        EventBus.getDefault().post(msg);
                        return;
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                HashMap<String,String> msg = new HashMap<String, String>();
                msg.put("UID",dataSnapshot.getKey());
                EventBus.getDefault().post(msg);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_STICKY;
    }
}
