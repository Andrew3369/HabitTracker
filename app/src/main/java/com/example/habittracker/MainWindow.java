//Filename:		MainWindow.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549,
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the MainWindow to work

package com.example.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainWindow extends AppCompatActivity
{
    Button addHabitButton = null; // start
    Button habitsButton = null; // view habits

    private static DatabaseHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwindow);

        // Create db helper
        databaseHelper = new DatabaseHelper(this);

        // Setup view: ADD HABIT
        addHabitButton = findViewById(R.id.main_button_addHabit);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainWindow.this, AddHabit.class);
                startActivity(intent);
            }
        });

        // Setup view: HABITS
        habitsButton = findViewById(R.id.main_button_viewHabits);
        habitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainWindow.this, ViewHabits.class);
                startActivity(intent);
            }
        });
    }

    public static DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }
}
