package com.example.ts; 
import java.util.ArrayList;
import java.util.List;

import com.example.ts.models.EventsDb;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class EventsDbHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "ts";
 
    // Contacts table name
    public static final String TABLE_E = "events";
 
    // Contacts Table Columns names
    public static final String KEY_ID = "_id";
    public  static final String KEY_NAME = "_name";
    public  static final String KEY_DES = "des";
    public  static final String KEY_C1 = "coordinator_1";
    public  static final String KEY_C2= "coordinator_2";
    public  static final String KEY_P1 = "phoneno_1";
    public  static final String KEY_P2 = "phoneno_2";
    public  static final String KEY_RULES = "rules";
    public  static final String KEY_VENUE= "venue";
    public  static final String KEY_DATE = "date";
    public EventsDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_E + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DES + " TEXT," + KEY_C1 + " TEXT," + KEY_C2 + " TEXT," +
                KEY_P1 + " TEXT," + KEY_P2 + " TEXT," +KEY_RULES + " TEXT," +
                KEY_VENUE + " TEXT," +KEY_DATE +" TEXT" +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addEvents(EventsDb e) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, e.getName()); // Contact Name
        values.put(KEY_DES, e.getDescription());
        values.put(KEY_C1, e.getCoordinator_1());
        values.put(KEY_C2, e.getCoordinator_2());
        values.put(KEY_P1, e.getPhone_no1());
        values.put(KEY_P2, e.getPhone_no2());
        values.put(KEY_DATE, e.getDate());
        values.put(KEY_RULES, e.getRules());
        values.put(KEY_VENUE, e.getVenue());
        // Inserting Row
        db.insert(TABLE_E, null, values);
        db.close(); // Closing database connection
    }
    
    public void insertData(String id,String name, String d,String c1,String c2,String p1,String p2,String date,String v,String rules)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("_id",id);
        values.put("_name",name);
        values.put("des",d);
        values.put("coordinator_1",c1);
        values.put("coordinator_2",c2);
        values.put("phoneno_1",p1);
        values.put("phoneno_2",p2);
        values.put("rules",rules);
        values.put("venue",v);
        values.put("date",date);
        sqLiteDatabase.insert("events",null,values);
    }
 
    // Getting single contact
    EventsDb getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_E, new String[] { KEY_ID,
                KEY_NAME, KEY_DES, KEY_C1,KEY_C2,KEY_P1,KEY_P2,KEY_RULES,KEY_DATE,KEY_VENUE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        EventsDb e = new EventsDb(Integer.parseInt(cursor.getString(0)),
cursor.getString(1), cursor.getString(2),cursor.getString(3) ,cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
        // return contact
        return e;
    }
     
   /* // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
 
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }
 
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 */
}