package com.example.womensafetyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserDBHelper extends SQLiteOpenHelper {
    public UserDBHelper(Context context) {
        super(context, "USERDATABSE", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table vehicleinfo (vehicleno text, details text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
