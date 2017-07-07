package com.example.amr.taskysolution;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    String CONTACTS_TABLE_NAME;
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_TITLE = "title";
    public static final String CONTACTS_COLUMN_PUBLISH_DATE = "published_date";
    public static final String CONTACTS_COLUMN_IMAGEURL = "imageurl";

    public DBHelper(Context context, String CONTACTS_TABLE_NAME) {
        super(context, DATABASE_NAME, null, 1);
        this.CONTACTS_TABLE_NAME = CONTACTS_TABLE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME + " (id integer primary key AUTOINCREMENT, gender text, salary text, imageurl text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String title, String published_date, String imageurl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_TITLE, title);
        contentValues.put(CONTACTS_COLUMN_PUBLISH_DATE, published_date);
        contentValues.put(CONTACTS_COLUMN_IMAGEURL, imageurl);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }

//    public ArrayList<Sample> getAllData() {
//        ArrayList<Sample> array_list = new ArrayList<Sample>();
//        Sample c;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from automobiles", null);
//        res.moveToFirst();
//
//        while (res.isAfterLast() == false) {
//            c = new Sample(res.getString(res.getColumnIndex(CONTACTS_COLUMN_TITLE)), res.getString(res.getColumnIndex(CONTACTS_COLUMN_PUBLISH_DATE)), res.getString(res.getColumnIndex(CONTACTS_COLUMN_IMAGEURL)));
//            array_list.add(c);
//            res.moveToNext();
//        }
//        return array_list;
//    }
}
