package com.ctbarbanza.gupyou;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ctbarbanza.gupyou.models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class DbController {

    private static final String TAG = DbController.class.getSimpleName().toLowerCase();

    private FirebaseDatabase database;

    private static final DbController instance = new DbController();
    private DbController(){
        initDb();
    }
    public static DbController init(){
        instance.initDb();
        return instance;
    }

    public static void saveUser(User user) {
        DatabaseReference ref = instance.database.getReference("users");
        ref.child(user.uid).setValue(user);
    }

    private void initDb() {
        database = FirebaseDatabase.getInstance();
    }


    public static void get(String uid) {
        if (uid == null ||uid.isEmpty()){
            return;
        }

        DatabaseReference ref = instance.database.getReference(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Value is: " + value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
