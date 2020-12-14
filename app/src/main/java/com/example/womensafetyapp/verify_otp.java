package com.example.womensafetyapp;



import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;


public class verify_otp extends AppCompatActivity {

    DatabaseReference databaseReference;

    //These are the objects needed
    //It is the verification id that will be sent to the user
    private String mVerificationId;

    //The edittext to input the code
    private EditText editTextCode;

    //firebase auth object
    private FirebaseAuth mAuth, mAuth1;
    private ProgressBar progressbar;

    //  public String name , username,dob , email , password , voterid , mobile;
    public String n1, m1, e1, ep1, p1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");


        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressbar);



        Intent i = getIntent();


        //  String id=i.getStringExtra("id");
        n1 = i.getStringExtra("name ");
        m1 = i.getStringExtra("mobile");


        e1 = i.getStringExtra("email ");
        ep1 = i.getStringExtra("emerpin ");
        p1 = i.getStringExtra("password ");



        verifyemail(e1, p1);
    }

    private void verifyemail(final String e1, final String p1) {
        mAuth.createUserWithEmailAndPassword(e1, p1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    String uid = task.getResult().getUser().getUid();

                    Toast.makeText(getApplicationContext(), "u id is " + uid, Toast.LENGTH_LONG).show();



                    UserInformation userInformation = new UserInformation(uid, n1, e1, ep1, p1, m1);
                    databaseReference.child(uid).setValue(userInformation);

                    Toast.makeText(getApplicationContext(), "Stored successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(verify_otp.this, user_Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                    Toast.makeText(getApplicationContext(), "  User Successfully  Created  ",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

                else {
                    //    mLoginProgress.hide();
                    Toast.makeText(getApplicationContext(), " Cannot Create User ",
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(verify_otp.this, MainActivity.class);

                    startActivity(i);


                }


            }
        });


    }
}