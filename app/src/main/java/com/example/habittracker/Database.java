//Filename:		Database.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549,
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the Database to work


package com.example.habittracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper
{

    private static final String databaseName = "habitDatabase.db";
    private static final int databaseVersion = 1;

    public Database(Context context)
    {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        try
        {
            database.execSQL // Create the Party table info
                    ("CREATE TABLE <put name here> (id INTEGER PRIMARY KEY, date TEXT, notes TEXT, budget INTEGER, theme TEXT)"
                    );
            Log.d("Database's Success", "Databases have been created");
        }
        catch (Exception error)
        {
            Log.e("Database error", "Database was not created");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS <put name of table here>");
        onCreate(sqLiteDatabase);
    }
}
