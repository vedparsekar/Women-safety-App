package com.example.womensafetyapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    public FirebaseUser mCurrentUser;

    MaterialTextView name, phone , email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name=(MaterialTextView) findViewById(R.id.fname);
        phone=(MaterialTextView) findViewById(R.id.fphone);
        email=(MaterialTextView) findViewById(R.id.femail);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mCurrentUser.getUid();



        DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference("User").child(current_id);
        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name1 = dataSnapshot.child("n1").getValue(String.class);
                name.setText(name1);

                String email1 = dataSnapshot.child("e1").getValue(String.class);
                email.setText(email1);

               String phone1 = dataSnapshot.child("m1").getValue(String.class);
               phone.setText(phone1);
                //Toast.makeText(getApplicationContext(), " hi " + acctname, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", databaseError.getMessage());
            }
        });

    }
}