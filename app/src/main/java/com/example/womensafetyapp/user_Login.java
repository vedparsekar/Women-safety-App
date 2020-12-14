package com.example.womensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class user_Login extends AppCompatActivity {

    TextInputEditText phone_auth, pass_auth;
    MaterialButton login_btn;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login);


        mAuth = FirebaseAuth.getInstance();


        phone_auth=(TextInputEditText)findViewById(R.id.phonenumber);
        pass_auth=(TextInputEditText)findViewById(R.id.passwordq);

        login_btn=(MaterialButton)findViewById(R.id.loginbtn);

        progressBar=(ProgressBar)findViewById(R.id.progressbar);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone12= phone_auth.getText().toString().trim();
                String password12=pass_auth.getText().toString().trim();
                login_user(phone12, password12);

            }
        });

    }


        private void login_user(String phone12, String password) {

            mAuth.signInWithEmailAndPassword(phone12, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        String pass= pass_auth.getText().toString().trim();
                       // mLoginProgress.dismiss();
                        Intent mainintent = new Intent(user_Login.this,UserProfile.class);

                        mainintent.putExtra("password",pass );

                        mainintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainintent);


                        Toast.makeText(getApplicationContext(), "Logged In successfully",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }

                    else{
                   //     mLoginProgress.hide();
                        Toast.makeText(getApplicationContext(), "Login Failed,please check email/password",
                                Toast.LENGTH_LONG).show();
                    }


                }
            });

    }
}
