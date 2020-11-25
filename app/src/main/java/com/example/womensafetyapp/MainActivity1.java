package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity1 extends AppCompatActivity {

    TextInputEditText namee , phonee, emaill, passwordd, repasswordd;
    Button registerr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        namee=(TextInputEditText)findViewById(R.id.name);
        phonee=(TextInputEditText)findViewById(R.id.phone);
        emaill=(TextInputEditText)findViewById(R.id.email);
        passwordd=(TextInputEditText)findViewById(R.id.password);
        repasswordd=(TextInputEditText)findViewById(R.id.re_password);


    }
}