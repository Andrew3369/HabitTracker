//Filename:		Services.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549,
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the Async Service to work

package com.example.habittracker;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Services extends Service
{
    private static final String fileName = "checked_habits.txt";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // Get the list of checked checkboxes (you need to implement this part)
        List<String> checkedHabits = getCheckedHabits();

        // Write checked checkboxes to a text file asynchronously
        AsyncTask.execute(() ->
        {
            writeToFile(checkedHabits);
        });

        return START_NOT_STICKY;
    }

    private List<String> getCheckedHabits()
    {
        // Store all the checkbox habits in here
        List<String> checkedHabits = new ArrayList<>();

        // Add code to retrieve checked checkboxes


        return checkedHabits; // then return the list >:)
    }

    private void writeToFile(List<String> checkedHabits)
    {
        try
        {
            // Open the file in append mode
            FileWriter writer = new FileWriter(getFilesDir() + "/" + fileName, true);

            // Write each checked habit to the file
            for (String habit : checkedHabits)
            {
                writer.write(habit + "\n");
            }

            // Close
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null; // idk stops the compiler from crying
    }
}
