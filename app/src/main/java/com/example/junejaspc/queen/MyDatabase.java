package com.example.junejaspc.queen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by junejaspc on 3/15/2017.
 */

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DB_name="";
    private static final int DB_version=1;
    String query;
    public MyDatabase(Context context) {
        super(context,DB_name, null,DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
