package khoavin.sillylearningenglish.NetworkService.Implementation;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import khoavin.sillylearningenglish.NetworkService.Interfaces.IUserService;

/**
 * Created by KhoaVin on 3/3/2017.
 */

public class UserService implements IUserService {

    private static final String TAG = "PersonalSerice";
    DatabaseReference presenceRef = FirebaseDatabase.getInstance().getReference().child("/presence");

    public UserService() {

    }
}
