//Filename:		AlarmReceiver.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549, 8896386, 8825169
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the Alarm Receiver to work

package com.example.habittracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("com.example.habittracker.ALARM_TRIGGERED")) {
                long habitId = intent.getLongExtra("habitId", -1);
                Log.d(TAG, "Received alarm for habit ID: " + habitId);

                // TODO: update the 'completed' value for the habit
            }
        }
    }
}
