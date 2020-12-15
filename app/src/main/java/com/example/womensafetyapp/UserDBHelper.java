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
        String table1 = "create table vehicleinfo (vehicleno text, details text)";
        String table2 = "create table emergency_contact (contactName text, contactNo text)";
        sqLiteDatabase.execSQL(table1);
        sqLiteDatabase.execSQL(table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS vehicleinfo");
        db.execSQL("DROP TABLE IF EXISTS emergency_contact");
        onCreate(db);
    }
}
