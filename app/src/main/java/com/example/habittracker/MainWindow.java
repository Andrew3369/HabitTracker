//Filename:		MainWindow.java
//Assignment:	A-01
//Author:		Andrew Babos
//Student #:	8822549
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the MainWindow to work

package com.example.habittracker;

import android.os.Bundle;
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
    }
}
