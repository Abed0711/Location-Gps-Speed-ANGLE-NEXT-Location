package com.abed.next;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private Context context;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public DBManager(Context c) {
        this.context = c;
    }

    public DBManager open() throws SQLException {
        this.dbHelper = new SQLiteHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insert(String Number) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(SQLiteHelper.Number, Number);
        this.database.insert(SQLiteHelper.TABLE_NAME_SHIP, null, contentValue);
    }


    public Cursor fetch() {
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_SHIP, new String[]{SQLiteHelper.Number}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String Number) {
        int _id = 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.Number, Number);
        return this.database.update(SQLiteHelper.TABLE_NAME_SHIP, contentValues, "Number !=" +0, null);
    }


}