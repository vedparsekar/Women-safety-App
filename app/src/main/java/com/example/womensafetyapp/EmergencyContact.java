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

public class EmergencyContact extends AppCompatActivity {

    DBClient dbClient;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        add = (Button) findViewById(R.id.add_btn);
        dbClient = new DBClient(this);
        dbClient.open();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddEmergencyContact.class);
                startActivity(intent);
            }
        });

        //error here....
        ArrayList<HashMap<String, String>> emergencyList = dbClient.GetEmergency_contacts();
        
        ListView lv = (ListView) findViewById(R.id.contact_list);
        ListAdapter adapter = new SimpleAdapter(getApplicationContext(), emergencyList, R.layout.list_row,new String[]{"contactName","contactNo"}, new int[]{R.id.name, R.id.designation});
        lv.setAdapter(adapter);

    }
}