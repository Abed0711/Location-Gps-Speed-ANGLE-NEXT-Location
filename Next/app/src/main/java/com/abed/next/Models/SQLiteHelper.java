package com.abed.next.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String AGE = "age";
    private static final String CREATE_TABLE_SHIP = "create table SHIP_NUM (Number TEXT )";
    private static final String CREATE_TABLE_SHIP_INFO = "create table SHIP_INFO (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,Lat Text,Lon TEXT )";
    private static final String CREATE_TABLE_SHIP_UPDATE= "create table SHIP_UPDATE (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,Lat Text,Lon TEXT )";
    private static final String DB_NAME = "SHIP.DB";
    public static final String Number = "Number";
    public static final String TABLE_NAME_SHIP = "SHIP_NUM";
    public static final String TABLE_NAME_SHIP_INFO = "SHIP_INFO";
    public static final String TABLE_NAME_SHIP_UPDATE = "SHIP_UPDATE";
    public static final String ID = "id";
    public static final String name = "name";
    public static final String Lat = "Lat";
    public static final String Lon = "Lon";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, 5);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHIP);
        db.execSQL(CREATE_TABLE_SHIP_INFO);
        db.execSQL(CREATE_TABLE_SHIP_UPDATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SHIP_NUM");
        db.execSQL("DROP TABLE IF EXISTS SHIP_INFO");
        db.execSQL("DROP TABLE IF EXISTS SHIP_UPDATE");
        onCreate(db);
    }


}