package com.example.android_ergasia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
Kollias Anastasios
 */

public class ChangePass extends AppCompatActivity {

    EditText e1,e2,e3,e4;
    Button b1;
    dbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        db = new dbHelper(this);
        e1 = (EditText)findViewById(R.id.unameCP);
        e2 = (EditText)findViewById(R.id.oldpass);
        e3 = (EditText)findViewById(R.id.newpass);
        e4 = (EditText)findViewById(R.id.cnewpass);
        b1 = (Button)findViewById(R.id.chgpass);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                String s4 = e4.getText().toString();
                Boolean validcreds = db.validcreds(s1, s2);

                if (s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill the empty fields.",Toast.LENGTH_SHORT).show();
                }
                else if(validcreds==false){
                    Toast.makeText(getApplicationContext(), "The credentials of your current account are wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
                else if(s3.equals(s4))
                {
                    Boolean changePass = db.changePass(s1,s2,s3);
                    if (changePass==true)
                    {
                        Toast.makeText(getApplicationContext(),"Password Change Successful.",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ChangePass.this,Login.class); //going to the login activity
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Password Change Failed.",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"New passwords do not match.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
