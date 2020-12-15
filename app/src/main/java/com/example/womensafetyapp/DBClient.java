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
    ArrayList<String> emergency_contacts;

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

    public ArrayList<String> getemergency_contact(){
        String query="select * from emergency_contact";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor!=null&&cursor.getCount()>0) {
            cursor.moveToFirst();
            emergency_contacts = new ArrayList<>();
            emergency_contacts.add(cursor.getString(0));
            emergency_contacts.add(cursor.getString(1));
            cursor.close();
            return emergency_contacts;
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

    public ArrayList<HashMap<String, String>> GetEmergency_contacts(){
        ArrayList<HashMap<String, String>> emergencyList = new ArrayList<>();
        String query = "SELECT contactName, contactNo FROM emergency_contact";
        Cursor cursor = database.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> contact = new HashMap<>();
            contact.put("contactName",cursor.getString(cursor.getColumnIndex("contactName")));
            contact.put("contactNo",cursor.getString(cursor.getColumnIndex("contactNo")));
            emergencyList.add(contact);
        }
        return  emergencyList;
    }


    public void addUser(String vehicleno,String details){
        ContentValues params = new ContentValues();
        params.put("vehicleno",vehicleno);
        params.put("details",details);
        database.insert("vehicleinfo",null,params);
    }

    public void addContact(String contactName,String contactNo){
        ContentValues params = new ContentValues();
        params.put("contactName",contactName);
        params.put("contactNo",contactNo);
        database.insert("emergency_contact",null,params);
    }

}
