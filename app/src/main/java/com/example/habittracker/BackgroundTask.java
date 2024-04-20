//Filename:		BackgroundTask.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549, 8896386, 8825169
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the Alarm Receiver to work

package com.example.habittracker;

import android.os.AsyncTask;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

public class BackgroundTask extends AsyncTask<Void, Void, Habit> {

    private WeakReference<MainWindow> main;
    private WeakReference<Context> context;
    private DatabaseHelper dbHelper;

    public BackgroundTask(Context context) {
        this.main = new WeakReference<>((MainWindow) context);
        this.context = new WeakReference<>(context);
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    protected Habit doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Habit closestTrip) {
        new FetchAPI(context.get()).execute();
    }
}
