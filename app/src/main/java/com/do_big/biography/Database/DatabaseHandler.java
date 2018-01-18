package com.do_big.biography.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

/**
 * Created by dell on 06-10-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bioStory";
    private static final String TABLE_CONTACTS = "english";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_STORY = "story";
    private static final String KEY_STORY_CATEGORY = "category";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_STORY + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public String getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS,
                                new String[] { KEY_ID, KEY_TITLE, KEY_STORY },
                                KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        String str=cursor.getString(1)+cursor.getString(2);
        return str;
    }
    public void addContact(Storys storys) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, storys. getStoryTitle()); // Contact Name
        values.put(KEY_STORY, storys.getStory()); // Contact Phone
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }
    // Getting All Contacts
    public Cursor getAllContacts() {
        LinkedList<Storys> contactList = new LinkedList<>();
        String selectQuery = "SELECT *  FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
/*
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StoryList story=new StoryList();
                String title =  story.setStoryTitle(cursor.getString(1));
                String strstory = story.setStory(cursor.getString(2));
                contactList.add(story);
            } while (cursor.moveToNext());
        }*/
        return cursor;
    }
}