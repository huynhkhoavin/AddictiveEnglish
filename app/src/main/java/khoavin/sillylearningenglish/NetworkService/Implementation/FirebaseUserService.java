package khoavin.sillylearningenglish.NetworkService.Implementation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import khoavin.sillylearningenglish.FirebaseObject.FirebaseAccount;
import khoavin.sillylearningenglish.NetworkService.Interfaces.IFirebaseUserService;

/**
 * Created by KhoaVin on 2/28/2017.
 */

public class FirebaseUserService implements IFirebaseUserService {
    DatabaseReference presenceRef = FirebaseDatabase.getInstance().getReference().child("presence");
    private String TAG = "FirebaseUserService";
    @Override
    public void checkOnlineStatus(FirebaseAccount[] firebaseAccounts) {
        presenceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
