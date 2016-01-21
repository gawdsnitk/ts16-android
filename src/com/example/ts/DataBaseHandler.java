package com.example.ts;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "ts";
 
    // Contacts table name
    private static final String TABLE_MESSAGES = "notifications";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NOTIFICATION = "notification";
    
 
    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NOTIFICATION + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
 
        // Create tables again
        onCreate(db);
    }
    public void addMessage(String message) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_NOTIFICATION, message); // Contact Name
        
     
        // Inserting Row
        db.insert(TABLE_MESSAGES, null, values);
        db.close(); // Closing database connection
    }
    
    public List<Noti> getAllMessages() {
        List<Noti> notiList = new ArrayList<Noti>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Noti msg = new Noti();
                msg.setID(Integer.parseInt(cursor.getString(0)));
                msg.setNoti(cursor.getString(1));
                notiList.add(msg);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return notiList;
    }
}