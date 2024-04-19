//Filename:		AddHabit.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549,
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the AddHabit to work

package com.example.habittracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class AddHabit extends AppCompatActivity
{

    private EditText habitNameEditText;
    private LinearLayout checkboxContainer;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addhabit);

        habitNameEditText = findViewById(R.id.habitNameEditText);
        checkboxContainer = findViewById(R.id.checkboxContainer);

        // Back action
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(callback);

        // Cancel button
        back = findViewById(R.id.cancelButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onAddHabitClick(View view) {
        String habitName = habitNameEditText.getText().toString().trim();

        // Habit name cannot be empty
        if (habitName.isEmpty()) {
            Toast.makeText(this, "Habit must have a name", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            habitNameEditText.setText("");  // clear input field

            Habit habit = new Habit(habitName);

            DatabaseHelper dbHelper = MainWindow.getDatabaseHelper();
            if (dbHelper != null) {
                long habitId = habit.saveToDatabase(dbHelper.getWritableDatabase());
                if (habitId != -1) {
                    Toast.makeText(this, "Habit added!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch(Exception error)
        {
            Log.d("AddHabitDebug", "Error: " + error.toString());
            Toast.makeText(this, "Failed to add habit", Toast.LENGTH_SHORT).show();
        }
    }

//    private void BackButton(String habitName)
//    {
//        // intent the list and bring it back to the main screen
//        Toast.makeText(this, "This works lol", Toast.LENGTH_SHORT).show();
//    }

    private void createCheckbox(String habitName)
    {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(habitName);
        checkboxContainer.addView(checkBox);
    }

}
