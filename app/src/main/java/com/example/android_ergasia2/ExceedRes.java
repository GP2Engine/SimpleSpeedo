package com.example.android_ergasia2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

/*
Kollias Anastasios MPSP19018
Ergasia 2 - Android
 */


public class ExceedRes extends AppCompatActivity {

    dbHelper db;
    TextView t1,t2,t3,t4,t5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exceed_res);
        db = new dbHelper(this);
        t1=(TextView)findViewById(R.id.userres);
        t2=(TextView)findViewById(R.id.lonres);
        t3=(TextView)findViewById(R.id.latres);
        t4=(TextView)findViewById(R.id.speedres);
        t5=(TextView)findViewById(R.id.timeres);
        String timestamp = getIntent().getStringExtra("transfer"); //getting the timestamp from the previous activity

        Cursor c = db.getUserExcByTime(timestamp);
        c.moveToNext();
        t1.setText(c.getString(c.getColumnIndex("uname")));
        t2.setText(c.getString(c.getColumnIndex("longitude")));
        t3.setText(c.getString(c.getColumnIndex("latitude")));
        t4.setText(c.getString(c.getColumnIndex("speed")));
        t5.setText(c.getString(c.getColumnIndex("timestamp")));
    }
}
