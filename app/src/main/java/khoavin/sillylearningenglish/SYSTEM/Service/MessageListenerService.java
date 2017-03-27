package khoavin.sillylearningenglish.SYSTEM.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        //Intent i = new Intent("MESSAGE_NOTIFY").putExtra("NEW_CHAT_ADD", dataSnapshot.getKey().toString());
        //sendBroadcast(i);
        ChatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Tin nhan chua duoc doc se duoc goi
                Intent i = new Intent("MESSAGE_NOTIFY").putExtra("UID", dataSnapshot.getKey());
                sendBroadcast(i);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Intent i = new Intent("MESSAGE_NOTIFY").putExtra("CHANGE", dataSnapshot.getKey());
//                sendBroadcast(i);
                Intent i = new Intent("MESSAGE_NOTIFY").putExtra("UID", dataSnapshot.getKey());
                sendBroadcast(i);
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
