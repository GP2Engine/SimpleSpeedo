package com.example.android_ergasia2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.widget.SwitchCompat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import java.util.Formatter;
import java.util.Locale;

/*
Kollias Anastasios
 */

public class SpeedoMeter extends AppCompatActivity implements LocationListener {

    dbHelper db;
    Button b1;
    TextView t1;
    SwitchCompat sw_metric;
    LinearLayout layout;
    LocationManager locationManager;
    boolean gpspermgranted;
    boolean gpsenabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedo_meter);
        db = new dbHelper(this);
        b1 = (Button) findViewById(R.id.exitSM);
        t1 = (TextView) findViewById(R.id.cspeed);
        sw_metric = findViewById(R.id.sw_metric);
        layout = findViewById(R.id.speedlayout);
        gpspermgranted = false;
        gpsenabled = false;

        //Checking for GPS permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
        } else {
            gpspermgranted = true;
            doStuff();
        }

        try {
            gpsenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        if(!gpsenabled) {
            //Prompting user
            enableGPSPrompt(this);
        }

        this.updateSpeed(null);

        sw_metric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpeedoMeter.this.updateSpeed(null);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SpeedoMeter.this,Menu.class); //going to the menu activity
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gpspermgranted) {                             //otherwise the app would crash when it didn't have the permission
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            CLocation myLocation = new CLocation(location, this.useMetricUnits());
            this.updateSpeed(myLocation);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @SuppressLint("MissingPermission")
    private void doStuff(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        }
        Toast.makeText(this,"Waiting for GPS Signal...",Toast.LENGTH_SHORT).show();
    }

    private void updateSpeed(CLocation location){
        float nCurrentSpeed = 0;
        if(location != null){
            location.setUseMetricUnits(this.useMetricUnits());
            nCurrentSpeed = location.getSpeed();
        }
        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US,"%5.1f",nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace(" ","0");

        int speedlimit = db.getSpeedLimit();
        if(this.useMetricUnits()){                   //for km/h
            t1.setText(strCurrentSpeed + " km/h");
            if (nCurrentSpeed>speedlimit){
                boolean insert = db.insertExcess(Login.unamewel,location.getLongitude(),location.getLatitude(),nCurrentSpeed);
                if(!insert){
                    Toast.makeText(getApplicationContext(),"Speed Exceedance Logging Failed",Toast.LENGTH_SHORT).show();
                }
                layout.setBackgroundColor(Color.RED);
            } else {
                layout.setBackgroundColor(Color.GREEN);
            }
        }
        else{                                     //for mph
            t1.setText(strCurrentSpeed + " mph");
            if (nCurrentSpeed>(speedlimit*0.621371192f)){
                boolean insert = db.insertExcess(Login.unamewel,location.getLongitude(),location.getLatitude(),(nCurrentSpeed*1.609344f)); //saving the speed in km/h in DB
                if(!insert){
                    Toast.makeText(getApplicationContext(),"Speed Exceedance Logging Failed",Toast.LENGTH_SHORT).show();
                }
                layout.setBackgroundColor(Color.RED);
            } else {
                layout.setBackgroundColor(Color.GREEN);
            }
        }
    }

    private boolean useMetricUnits(){
        return sw_metric.isChecked();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                gpspermgranted = true;
                doStuff();
            } else {
                finish();
            }
        }
    }

    //Prompt for the user to enable GPS if not enabled.
    public void enableGPSPrompt(final Activity activity)
    {

        final AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Please enable Location Services in order for the speedometer to function.";

        builder.setMessage(message)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                Intent i = new Intent(SpeedoMeter.this, Menu.class);
                                activity.startActivity(i);
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                Intent i = new Intent(SpeedoMeter.this, Menu.class);
                                activity.startActivity(i);
                                d.dismiss();
                            }
                        });
        builder.create().show();
    }
}
