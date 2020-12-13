package com.example.womensafetyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<HashMap<String, String>> GetUsers(){
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT vehicleno, details FROM vehicleinfo";
        Cursor cursor = database.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("vehicleno",cursor.getString(cursor.getColumnIndex("vehicleno")));
            user.put("details",cursor.getString(cursor.getColumnIndex("details")));
            userList.add(user);
        }
        return  userList;
    }

    public void addUser(String vehicleno,String details){
        ContentValues params = new ContentValues();
        params.put("vehicleno",vehicleno);
        params.put("details",details);
        database.insert("vehicleinfo",null,params);
    }
}
