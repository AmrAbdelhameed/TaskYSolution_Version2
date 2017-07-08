package com.example.amr.taskysolution;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "api";
    public static final String CONTACTS_COLUMN_TITLE = "title";
    public static final String CONTACTS_COLUMN_PUBLISH_DATE = "publisheddate";
    public static final String CONTACTS_COLUMN_IMAGEURL = "imageurl";
    public static final String CONTACTS_COLUMN_STORY = "story";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table api " +
                        "(id integer primary key AUTOINCREMENT, title text, publisheddate text, imageurl text, story text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS api");
        onCreate(db);
    }

    public void insertData(String title, String published_date, String imageurl, String story) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_TITLE, title);
        contentValues.put(CONTACTS_COLUMN_PUBLISH_DATE, published_date);
        contentValues.put(CONTACTS_COLUMN_IMAGEURL, imageurl);
        contentValues.put(CONTACTS_COLUMN_STORY, story);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
    }

    public Integer deleteAll(String story) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("api",
                "story = ? ",
                new String[]{String.valueOf(story)});
    }

    public ArrayList<Sample> getAllData(String story) {
        ArrayList<Sample> array_list = new ArrayList<Sample>();
        Sample c;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from api WHERE story = ?", new String[]{story});
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            c = new Sample();

            c.setTitle(res.getString(res.getColumnIndex(CONTACTS_COLUMN_TITLE)));
            c.setPublished_date(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PUBLISH_DATE)));
            c.setUrl(res.getString(res.getColumnIndex(CONTACTS_COLUMN_IMAGEURL)));

            array_list.add(c);
            res.moveToNext();
        }
        return array_list;
    }
}
