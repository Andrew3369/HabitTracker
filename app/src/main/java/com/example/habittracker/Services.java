//Filename:		Services.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549, 8896386, 8825169
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

        return START_NOT_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
