package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EmergencyContact extends AppCompatActivity {

    DBClient dbClient;
    Button add;
    ListView lv;
    Integer indexVal;
    String item;

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


        final ArrayList<HashMap<String, String>> emergencyList = dbClient.GetEmergency_contacts();
        
        lv = (ListView) findViewById(R.id.contact_list);
        final ListAdapter adapter = new SimpleAdapter(getApplicationContext(), emergencyList, R.layout.list_row,new String[]{"contactName","contactNo"}, new int[]{R.id.name, R.id.designation});
        lv.setAdapter(adapter);


/*        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString() + "has been selected";
                indexVal = position;

                Toast.makeText(EmergencyContact.this, "value " + indexVal, Toast.LENGTH_SHORT).show();

                return true;
            }
        });*/

    }
}