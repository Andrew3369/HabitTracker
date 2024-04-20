package com.example.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Habit {
    public static final String TABLE_NAME = "habits";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FREQUENCY = "frequency";
    public static final String COLUMN_COMPLETED = "completed";

    // Database creation SQL statement
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT CHECK(LENGTH(" + COLUMN_NAME + ") <= 50)" +
                    COLUMN_FREQUENCY + " TEXT," +
                    COLUMN_COMPLETED + " INTEGER DEFAULT 0)";

    private long id;
    private String name;
    private String frequency;
    private boolean completed;

    public Habit(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
        this.completed = false;
    }

    // Getter and setter methods
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Database operations

    public long saveToDatabase(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_FREQUENCY, frequency);
        values.put(COLUMN_COMPLETED, completed ? 1 : 0);

        return db.insert(TABLE_NAME, null, values);
    }

    public static Habit loadFromDatabase(SQLiteDatabase db, long habitId) {
        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_ID + " = ?", new String[]{String.valueOf(habitId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int frequencyIndex = cursor.getColumnIndex(COLUMN_FREQUENCY);
            int completedIndex = cursor.getColumnIndex(COLUMN_COMPLETED);

            if (nameIndex != -1 && completedIndex != -1) {
                String name = cursor.getString(nameIndex);
                String frequency = cursor.getString(frequencyIndex);
                boolean completed = cursor.getInt(completedIndex) == 1;

                cursor.close();
                return new Habit(habitId, name, frequency, completed);
            } else {
                // TODO: handle this case better...
                cursor.close();
                return null;
            }
        }

        return null;
    }

    private Habit(long id, String name, String frequency, boolean completed) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
        this.completed = completed;
    }

    public void updateCompletionStatus(SQLiteDatabase db, boolean completed) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETED, completed ? 1 : 0);

        db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        this.completed = completed;
    }

    public void deleteFromDatabase(SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
