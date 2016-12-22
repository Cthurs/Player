package com.cha.mp3;

import android.content.Context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by C on 2015-12-05.
 */
public class MyDB extends SQLiteOpenHelper{
    public final static int DB_VER =1;
    public MyDB(Context context) {
        super(context, "AB", null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE repeat (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, title TEXT, start DOUBLE, end DOUBLE, ab DOUBLE, path TEXT, abcp INTEGER);";
//        String sql = "CREATE TABLE bookmark (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, title TEXT, start DOUBLE, path TEXT, bmcp INTEGER);";
        db.execSQL(sql);

        String sql2 = "CREATE TABLE currentP (_id INTEGER PRIMARY KEY AUTOINCREMENT, cp INTEGER);";
        db.execSQL(sql2);

        String sql3 = "INSERT INTO currentP VALUES(1, 0);";
        db.execSQL(sql3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS repeat";
        db.execSQL(sql);
        String sql2 = "DROP TABLE IF EXISTS currentP";
        db.execSQL(sql2);
        onCreate(db);
    }
}
