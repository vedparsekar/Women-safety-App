package com.example.womensafetyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBClient {
    UserDBHelper dbhelper;
    Context context;
    SQLiteDatabase database;
    ArrayList<String> users;

    public  DBClient(Context context){
        this.context = context;
    }

    public void open(){
        dbhelper = new UserDBHelper(context);
        database = dbhelper.getWritableDatabase();
    }
    public void close(){
        dbhelper.close();
    }

    public ArrayList<String> getuser(){
        String query="select * from vehicleinfo";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor!=null&&cursor.getCount()>0) {
            cursor.moveToFirst();
            users = new ArrayList<>();
            users.add(cursor.getString(0));
            users.add(cursor.getString(1));
            cursor.close();
            return users;
        }
        else
            return null;
    }

    public void addUser(String vehicleno,String details){
        ContentValues params = new ContentValues();
        params.put("vehicleno",vehicleno);
        params.put("details",details);
        database.insert("vehicleinfo",null,params);
    }
}
