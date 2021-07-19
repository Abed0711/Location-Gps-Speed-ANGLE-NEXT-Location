package com.abed.next.DbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.abed.next.Models.SQLiteHelper;

public class DBManagerLatLon {
    private Context context;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public DBManagerLatLon(Context c) {
        this.context = c;
    }

    public DBManagerLatLon open() throws SQLException {
        this.dbHelper = new SQLiteHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insertSInfo(String name, String Lat, String Lon) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(SQLiteHelper.name, name);
        contentValue.put(SQLiteHelper.Lat, Lat);
        contentValue.put(SQLiteHelper.Lon, Lon);
        this.database.insert(SQLiteHelper.TABLE_NAME_SHIP_INFO, null, contentValue);
    }


    public Cursor fetchData() {
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_SHIP_INFO, new String[]{SQLiteHelper.ID, SQLiteHelper.name, SQLiteHelper.Lat, SQLiteHelper.Lon}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateInfo(int id, String name, String Lat, String Lon) {
        //int _id = 1;
        ContentValues contentValue = new ContentValues();
        contentValue.put(SQLiteHelper.name, name);
        contentValue.put(SQLiteHelper.Lat, Lat);
        contentValue.put(SQLiteHelper.Lon, Lon);
        return this.database.update(SQLiteHelper.TABLE_NAME_SHIP_INFO, contentValue, "id =" + id, null);
    }

    public void deleteInfo(long id) {
        this.database.delete(SQLiteHelper.TABLE_NAME_SHIP_INFO, "id=" + id, null);
    }

}