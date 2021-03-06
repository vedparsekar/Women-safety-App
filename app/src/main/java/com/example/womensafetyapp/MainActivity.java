package com.example.womensafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,LocationListener, SensorEventListener {
    Button sos;
    BottomNavigationView bottomNavigationView;
    MapView mapView;
    GoogleMap gmap;
    LocationManager locationManager;
    String provider;
    double lat=15.4871883,log=73.8265425;
    DBClient dbClient;

    String phoneNumber = "9764214269";
    String myLatitude,myLongitude;
    MediaPlayer mediaPlayer;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isAccelerometerSensorAvailable,notFirstTime = false;
    private float currentX,currentY,currentZ,lastX,lastY,lastZ;
    private float xDifference,yDifference,zDifference;
    private float shakeThreshold = 10f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sos = findViewById(R.id.sos_btn);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync((OnMapReadyCallback) this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.SEND_SMS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);


        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), Pin.class);
                //startActivity(intent);
              //  if(mediaPlayer == null)
                    StartSOS();
              //  {

               // }

               // else
                 //   StopSOS();
            }
        });


        //************Shaking Phone(gesture)***********//
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        }else{
            isAccelerometerSensorAvailable = false;
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.page_1) {
                    Intent intent = new Intent(getApplicationContext(), EmergencyContact.class);
                    startActivity(intent);
                }
                if (itemId == R.id.page_2) {
                    Intent intent = new Intent(getApplicationContext(), Camera.class);
                    startActivity(intent);
                }

                if (itemId == R.id.page_3) {
                    Intent intent = new Intent(getApplicationContext(), Vehicle.class);
                    startActivity(intent);
                }

                if (itemId == R.id.page_4) {
                    Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        statusCheck();

        locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {
            if (!provider.contains("gps")) { // if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50, 0, this);

            if (location != null)
                onLocationChanged(location);
            else
                location = locationManager.getLastKnownLocation(provider);
            if (location != null)
                onLocationChanged(location);
            else

                Toast.makeText(getBaseContext(), "Location can't be retrieved",
                        Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getBaseContext(), "No Provider Found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void StopSOS() {

        Intent i=new Intent(MainActivity.this,Pin.class);
        startActivity(i);




        /*
        // Stop sos
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer=null;
        }

         */
    }

    public void StartSOS(){

        Intent i= new Intent(MainActivity.this,Pin.class);
        i.putExtra("Lat", myLatitude);
        i.putExtra("Lon", myLongitude);

        dbClient = new DBClient(this);
        dbClient.open();
        ArrayList<HashMap<String, String>> userList = dbClient.GetEmergency_contacts();
        String message = "Hey I'm Stranded and unsafe. Help! \nhttps://maps.google.com/maps?q=" + myLatitude + "," + myLongitude;
        SmsManager smsManager = SmsManager.getDefault();

        for(int j=0;j<=(userList).size()-1;j++)
        {
            smsManager.sendTextMessage(userList.get(j).get("contactNo"), null, message, null, null);
            Toast.makeText(getApplicationContext(),"sms sent:"+userList.get(j).get("contactNo"),Toast.LENGTH_SHORT).show();
        }
        startActivity(i);
     //   if(mediaPlayer==null) {
      //      mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.police_siren1);
      //      mediaPlayer.start();
      //  }
        // Reading all contacts


//


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isAccelerometerSensorAvailable){
            sensorManager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        if(isAccelerometerSensorAvailable){
            sensorManager.unregisterListener(this);
        }
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);

        LatLng loc = new LatLng(lat, log);
        //LatLng loc = new LatLng(15.675675675675675, 73.7146768382642);
        gmap.addMarker(new MarkerOptions().position(loc).title("you're here"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(loc));

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        startActivity(new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        // Getting reference to TextView tv_longitude
        //TextView tvLongitude = findViewById(R.id.textView2);
        //tvLongitude.setText(String.valueOf(location.getLatitude()));

        // Getting reference to TextView tv_latitude
        //TextView tvLatitude = findViewById(R.id.textView3);
        //tvLatitude.setText(String.valueOf(location.getLongitude()));
        lat = location.getLatitude();
        log = location.getLongitude();

        myLatitude = String.valueOf(location.getLatitude());
        myLongitude = String.valueOf(location.getLongitude());

        // Setting Current Longitude
    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    0);
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

        if(notFirstTime){
            xDifference = Math.abs(lastX-currentX);
            yDifference = Math.abs(lastY-currentY);
            zDifference = Math.abs(lastZ-currentZ);

            if((xDifference > shakeThreshold) && (yDifference > shakeThreshold) ||
                    (xDifference > shakeThreshold) && (zDifference > shakeThreshold) ||
                    (yDifference > shakeThreshold) && (zDifference > shakeThreshold)){
                /*
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                }else{
                    vibrator.vibrate(500);
                }
                */
                StartSOS();
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        notFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}