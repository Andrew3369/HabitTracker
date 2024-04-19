//Filename:		AddHabit.java
//Assignment:	Final Project
//Author:		Andrew Babos, Hassan Alqhwaizi, Rhys Mccash
//Student #'s:	8822549,
//Date:			4/18/2024
//Description:	Contains the logic neccessary for the AddHabit to work

package com.example.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddHabit extends AppCompatActivity
{

    private EditText habitNameEditText;
    private LinearLayout checkboxContainer;

    private Button back;


    private List<String> habitList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addhabit);

        habitNameEditText = findViewById(R.id.habitNameEditText);
        checkboxContainer = findViewById(R.id.checkboxContainer);
        back = findViewById(R.id.backButton);
        if (back == null)
            Log.e("Error", "The ID 'planStart' was not found");
        else
        {
            back.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    // Go to next screen
                    Intent intent = new Intent(AddHabit.this, MainWindow.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void onAddHabitClick(View view)
    {
        try
        {
            String habitName = habitNameEditText.getText().toString().trim();
            if (!habitName.isEmpty())
            {
                createCheckbox(habitName);
                habitList.add(habitName); // add to the list
                habitNameEditText.setText(""); // Clear the input field
            }
            else
            {
                Toast.makeText(this, "Please enter a habit name", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception error)
        {
            Toast.makeText(this, "Error Adding Habit", Toast.LENGTH_SHORT).show();
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
