package com.example.android_ergasia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
Kollias Anastasios MPSP19018
Ergasia 2 - Android
 */

public class Login extends AppCompatActivity {

    EditText e1,e2;
    Button b1,b2;
    dbHelper db;
    public static String unamewel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new dbHelper(this);
        e1 = (EditText) findViewById(R.id.unameL);
        e2 = (EditText) findViewById(R.id.passL);
        b1 = (Button) findViewById(R.id.login);
        b2 = (Button) findViewById(R.id.gochgpass);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unameL = e1.getText().toString();
                String passL = e2.getText().toString();
                Boolean validcreds = db.validcreds(unameL, passL);
                if (unameL.equals("") || passL.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill the empty fields.", Toast.LENGTH_SHORT).show();

                } else {
                    if (validcreds == true) {
                        Toast.makeText(getApplicationContext(), "Logged in successfully.", Toast.LENGTH_SHORT).show();
                        e1.setText(""); //clearing the credentials on log in
                        e2.setText("");
                        unamewel = unameL;
                        Intent i = new Intent(Login.this, Menu.class); //going to the menu activity
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ChangePass.class); //going to the ChangePass activity
                startActivity(i);
            }
        });
    }
}
