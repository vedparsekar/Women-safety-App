package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Vehicle extends AppCompatActivity {
    DBClient dbClient;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        add =(Button) findViewById(R.id.add_btn);
        dbClient = new DBClient(this);
        dbClient.open();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddVehicleinfo.class);
                startActivity(intent);
            }
        });

        ArrayList<HashMap<String, String>> userList = dbClient.GetUsers();
        ListView lv = (ListView) findViewById(R.id.user_list);
        ListAdapter adapter = new SimpleAdapter(getApplicationContext(), userList, R.layout.list_row,new String[]{"vehicleno","details"}, new int[]{R.id.name, R.id.designation});
        lv.setAdapter(adapter);
    }
}