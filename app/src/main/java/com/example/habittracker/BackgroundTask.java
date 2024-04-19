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
        List<Habit> allHabits = dbHelper.getAllHabits();
        return allHabits.get(0);
    }

    @Override
    protected void onPostExecute(Habit closestTrip) {
        new FetchAPI(context.get()).execute();
    }
}
