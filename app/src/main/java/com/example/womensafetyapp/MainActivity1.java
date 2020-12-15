package com.example.womensafetyapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;


public class MainActivity1 extends AppCompatActivity
{

    // DatabaseReference databaseReference;

    MaterialTextView redirect_to_login1;

    TextInputEditText name1, mobile1, email1, emerpin1, pass1 , repass1;


    MaterialButton regme;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //     databaseReference = FirebaseDatabase.getInstance().getReference();

        redirect_to_login1=(MaterialTextView)findViewById(R.id.redirect_to_login);

        name1=(TextInputEditText)findViewById(R.id.name);

        mobile1=(TextInputEditText)findViewById(R.id.phone);
        email1=(TextInputEditText)findViewById(R.id.email);
        emerpin1=(TextInputEditText)findViewById(R.id.emerpin);
        pass1=(TextInputEditText)findViewById(R.id.password);
        repass1=(TextInputEditText)findViewById(R.id.re_password);




        regme = (MaterialButton) findViewById(R.id.regme);


        regme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                saveInfo();
            }
        });


        redirect_to_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new  Intent(MainActivity1.this,user_Login.class);
                startActivity(i);
            }
        });
    }

    public void saveInfo()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String n = name1.getText().toString().trim();

        String m = mobile1.getText().toString().trim();
        String e = email1.getText().toString().trim();
        String ep = emerpin1.getText().toString().trim();
        String p = pass1.getText().toString().trim();
        String rp = repass1.getText().toString().trim();

        Toast.makeText(getApplicationContext(),"mob "+m, Toast.LENGTH_LONG).show();

        if (e.matches(emailPattern) )
        {
            //  Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();

        }
        else
        {
            email1.setError("Invalid emailid ");
            email1.requestFocus();
            return;
        }

        if(n.isEmpty())
        {
            name1.setError("Please enter your name ");
            name1.requestFocus();
            return;
        }
        if (m.isEmpty() )
        {
            mobile1.setError("Phone number  cannot be empty");
            mobile1.requestFocus();
            return;
        }
        if (m.length()>10 || m.length()<10)
        {
            mobile1.setError("Please check your mobile number");
            mobile1.requestFocus();
            return;
        }

        if(e.isEmpty())
        {
            email1.setError("Email address cannot be empty");
            email1.requestFocus();
            return;
        }
        if (ep.isEmpty())

        {
            emerpin1.setError("please enter your password");
            emerpin1.requestFocus();
            return;
        }

        if(ep.length()!=4)
        {
            {
                emerpin1.setError("4 digit Pin is Required");
                emerpin1.requestFocus();
                return;
            }
        }

        if (p.isEmpty())
        {
            pass1.setError("Please Enter  your password");
            pass1.requestFocus();
            return;
        }
        if (rp.isEmpty())
        {
            repass1.setError("Retype your password");
            repass1.requestFocus();
            return;
        }

        if(p.equals(rp)!=true)
        {
            repass1.setError("should match");
            repass1.requestFocus();
            return;
        }


        // Toast.makeText(getApplicationContext(),""+tmpStr10,Toast.LENGTH_LONG).show();
        Intent i = new Intent(MainActivity1.this, verify_otp.class);

        //i.putExtra("id", id);
        i.putExtra("name ", n);
        i.putExtra("mobile", m);
        i.putExtra("email ", e);
        i.putExtra("emerpin ", ep);
        i.putExtra("password ", p);


        Toast.makeText(getApplicationContext(),"mobbbbbbbbbb "+m, Toast.LENGTH_LONG).show();

        startActivity(i);
    }
}
