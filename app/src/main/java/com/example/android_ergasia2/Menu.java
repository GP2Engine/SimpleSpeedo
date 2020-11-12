package com.example.android_ergasia2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/*
Kollias Anastasios MPSP19018
Ergasia 2 - Android
 */

public class Menu extends AppCompatActivity {

    dbHelper db;
    Button b1,b2,b3,b4;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = new dbHelper(this);
        b1=(Button)findViewById(R.id.startspeed);
        b2=(Button)findViewById(R.id.viewmyExs);
        b3 =(Button)findViewById(R.id.viewallExs);
        b4 =(Button)findViewById(R.id.logout);
        t1=(TextView)findViewById(R.id.weltext);
        t1.append(", "+Login.unamewel);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,SpeedoMeter.class); //going to the speedometer activity
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,UserExceedList.class); //going to the UserExceedList activity
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,UserExceedMaps.class); //going to the UserExceedMaps activity
                startActivity(i);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,MainActivity.class); //going to the main activity
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}
