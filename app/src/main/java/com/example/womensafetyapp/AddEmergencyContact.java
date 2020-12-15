package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddEmergencyContact extends AppCompatActivity {

    DBClient dbClient;
    EditText contactName,contactNo;
    private static final int RESULT_PICK_CONTACT = 1;
    ImageButton ReadBtn;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency_contact);

        contactName = (EditText) findViewById(R.id.contactName);
        contactNo = (EditText) findViewById(R.id.contactNo);
        ReadBtn = (ImageButton) findViewById(R.id.selectContact);
        add = (Button) findViewById(R.id.add_btn);

        ReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i,RESULT_PICK_CONTACT);
            }
        });


        dbClient = new DBClient(this);
        dbClient.open();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contactName.getText().toString().isEmpty() || contactNo.getText().toString().isEmpty()){
                    Toast.makeText(AddEmergencyContact.this, "Please fill out the details!", Toast.LENGTH_SHORT).show();
                }else{
                    dbClient.addContact(contactName.getText().toString(), contactNo.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), EmergencyContact.class);
                    startActivity(intent);
                }
            }
        });

    }

    //********************Reading Contacts******************//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        }
    }


    //***********Contact Picking from Phone Contacts***********//
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String phoneName = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();
            int phonIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int phonName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phonIndex);
            phoneName = cursor.getString(phonName);

            contactNo.setText(phoneNo);
            contactName.setText(phoneName);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}