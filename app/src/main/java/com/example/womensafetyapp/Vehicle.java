package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Vehicle extends AppCompatActivity {
    DBClient dbClient;
   TextView vno,vdet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        vno =(TextView)findViewById(R.id.vno);
        vdet =(TextView)findViewById(R.id.vdet);
        dbClient = new DBClient(this);
        dbClient.open();
        ArrayList<String> users;
        users = dbClient.getuser();
        if(users==null)
        {
        }else{
            vno.setText(users.get(0));
            vdet.setText(users.get(1));
        }
    }
}