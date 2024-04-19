package com.example.habittracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "habit_tracker.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Habit.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Habit.TABLE_NAME);
        onCreate(db);
    }

    public List<Habit> getAllHabits() {
        List<Habit> habitList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            String selectQuery = "SELECT * FROM " + Habit.TABLE_NAME;
            cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndexOrThrow(Habit.COLUMN_ID);

                do {
                    int id = cursor.getInt(idIndex);
                    Habit habit = Habit.loadFromDatabase(db, id);

                    habitList.add(habit);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return habitList;
    }
}
