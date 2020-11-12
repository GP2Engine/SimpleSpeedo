package com.example.android_ergasia2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
Kollias Anastasios
 */

public class UserExceedList extends AppCompatActivity {

    dbHelper db;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_exceed_list);
        db = new dbHelper(this);
        l1=(ListView) findViewById(R.id.listview);
        ArrayList<String> alist = new ArrayList<>();
        Cursor c = db.getUserExcesses(Login.unamewel);

        if(c.getCount() == 0){
            Toast.makeText(this, "No speed limit exceedances found for your account.",Toast.LENGTH_LONG).show();
            Intent i = new Intent(UserExceedList.this,Menu.class); //going to the menu activity
            startActivity(i);
        }
        else
        {
            while(c.moveToNext()){
                alist.add(c.getString(c.getColumnIndex("timestamp")));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alist);
                l1.setAdapter(listAdapter);
            }
            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clickinput = ((TextView) view).getText().toString();
                    Intent i = new Intent(UserExceedList.this,ExceedRes.class); //going to the exceedres activity
                    i.putExtra("transfer",clickinput); //sending the timestamp to the next activity so it can find the corresponding event details
                    startActivity(i);
                }
            });

        }
    }
}
