package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pin extends AppCompatActivity {

    Pinview pin;

    MaterialButton btn;
    String phoneNumber="9923854568";
    public FirebaseUser mCurrentUser;
    MediaPlayer mediaPlayer;

    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        Intent i= getIntent();
        String lat1= i.getStringExtra("Lat");
        String lon2= i.getStringExtra("Lon");

        pin = (Pinview) findViewById(R.id.otp_view);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.police_siren1);
        mediaPlayer.start();
         String message = "Hey I'm Stranded and unsafe. Help! \nhttps://maps.google.com/maps?q=" + lat1 + "," + lon2;
         SmsManager smsManager = SmsManager.getDefault();
         smsManager.sendTextMessage(phoneNumber, null, message, null, null);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mCurrentUser.getUid();
        final DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference("User").child(current_id);





        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {

            @Override
            public void onDataEntered(final Pinview pinview, boolean fromUser) {
                final String pin1= pin.getValue();
                Toast.makeText(Pin.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                DataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String pin12 = dataSnapshot.child("ep1").getValue(String.class);
                      //  name.setText(name1);


                        if (pinview.getValue().equals(pin12))
                        {
                            if(mediaPlayer!=null) {
                                mediaPlayer.stop();
                                mediaPlayer = null;

                                Intent i = new Intent(Pin.this, MainActivity.class);
                                startActivity(i);
                            }

                        }
                        else
                        {

                           Toast.makeText(getApplicationContext(),"Enter Correct Pin",Toast.LENGTH_LONG).show();
                        }



                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("error", databaseError.getMessage());
                    }
                });






            }
        });

       // btn.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {

         //       String pin1= pin.getValue();
         //           Toast.makeText(Pin.this,"" +pin.getValue(), Toast.LENGTH_LONG).show();
        //    }
      //  });
    }
}