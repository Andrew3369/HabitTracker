//Filename:		MainWindow.java
//Assignment:	A-01
//Author:		Andrew Babos
//Student #:	8822549
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the MainWindow to work

package com.example.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainWindow extends AppCompatActivity
{
    Button startHabit = null; // start
    Button viewHabits = null; // view habits


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwindow);


        startHabit = findViewById(R.id.planStart);
        if (startHabit == null)
            Log.e("Error", "The ID 'planStart' was not found");
        else
        {
            startHabit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    // Go to next screen
                    Intent intent = new Intent(MainWindow.this, AddHabit.class);
                    startActivity(intent);
                }
            });
        }


    }
}
