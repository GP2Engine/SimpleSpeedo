package com.example.android_ergasia2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*
Kollias Anastasios
 */

public class dbHelper extends SQLiteOpenHelper {
    public dbHelper(@Nullable Context context) {
        super(context, "mydatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table users(uname text primary key, pass text)");
        db.execSQL("Create table speedlimit(slimit int primary key)");
        db.execSQL("Create table speedexceed(id integer primary key AUTOINCREMENT, uname text, longitude double, latitude double, speed float, timestamp datetime)");

        db.execSQL("Insert into users(uname, pass) VALUES('tasos', '1234')");

        db.execSQL("Insert into speedlimit(slimit) VALUES(4)"); // Speedlimit Value in km/h
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
    }

    //insert users in database
    public boolean insertUser(String uname, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uname",uname);
        contentValues.put("pass",pass);
        long ins = db.insert("users", null, contentValues);
        if(ins==-1) return false;
        else return true;
    }

    //change users' password
    public boolean changePass(String uname, String oldpass, String newpass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uname",uname);
        contentValues.put("pass",newpass);
        long ins = db.update("users", contentValues, "uname=? and pass=?", new String[]{uname,oldpass});
        if(ins==-1) return false;
        else return true;
    }

    //Check for username duplicates
    public Boolean checkuname(String uname) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where uname=?", new String[]{uname});
        if (cursor.getCount()>0) return false;
        else return true;
    }

    //Check credentials' validity
    public Boolean validcreds(String uname,String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where uname=? and pass=?", new String[]{uname,pass});
        if(cursor.getCount()>0) return true;
        else return false;
    }

    //Getting the speedlimit value (in km/h) from the database
    public int getSpeedLimit(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from speedlimit", null);
        cursor.moveToNext();
        int speedlim = cursor.getInt(0);
        return speedlim;
    }

    //Getting current timestamp
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //insert speed limit excess events in database
    public boolean insertExcess(String uname, double longitude, double latitude, float speed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uname",uname);
        contentValues.put("longitude",longitude);
        contentValues.put("latitude",latitude);
        contentValues.put("speed",speed);
        contentValues.put("timestamp",getDateTime());
        long ins = db.insert("speedexceed", null, contentValues);
        if(ins==-1) return false;
        else return true;
    }

    //getting the logs of the speed limit exceedances of a specific user
    public Cursor getUserExcesses(String uname) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from speedexceed where uname=?", new String[]{uname});
        return cursor;
    }

    //getting the logs of the speed limit exceedances of all users
    public Cursor getAllExcesses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from speedexceed", null);
        return cursor;
    }

    //getting the logs of the speed limit exceedances of a specific timestamp
    public Cursor getUserExcByTime(String timestamp) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from speedexceed where timestamp=?", new String[]{timestamp});
        return cursor;
    }

}
