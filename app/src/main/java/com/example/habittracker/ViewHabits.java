package com.example.habittracker;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


/*
 * CLASS         : ViewHabits
 * DESCRIPTION   : This activity lets the user view all the habits they have saved.
 */
public class ViewHabits extends AppCompatActivity {
    private ArrayAdapter<Habit> adapter;
    private List<Habit> habitList;
    private DatabaseHelper dbHelper;


    /*
     * FUNCTION      : onCreate
     * DESCRIPTION   : This function initializes the activity and loads the habits from the database.
     * PARAMETERS    : Bundle savedInstanceState
     * RETURNS       : void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewhabits);

        // Back action
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(callback);

        // Add back button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Initialize ListView
        ListView listViewHabits = findViewById(R.id.viewhabits_listview);
        registerForContextMenu(listViewHabits);

        // Load habits
        try {
            dbHelper = new DatabaseHelper(this);
            habitList = dbHelper.getAllHabits(); // Retrieve habits from the database

            // Initialize ArrayAdapter with habit names
            adapter = new ArrayAdapter<Habit>(this, android.R.layout.simple_list_item_1, habitList) {
                @NonNull
                @Override
                public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                    View view = convertView;
                    if (view == null) {
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.habit_listitem, null);
                    }

                    Habit habit = getItem(position);
                    assert habit != null;

                    // Habit name
                    TextView habitNameTextView = view.findViewById(R.id.listitem_habit_textview_habitName);
                    habitNameTextView.setText(habit.getName());

                    // Habit completed
                    CheckBox habitCompletedCheckBox = view.findViewById(R.id.listitem_habit_checkbox_habitCompleted);
                    habitCompletedCheckBox.setChecked(habit.isCompleted());
                    habitCompletedCheckBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isChecked = ((CheckBox) v).isChecked();
                            habit.updateCompletionStatus(dbHelper.getWritableDatabase(), isChecked);
                        }
                    });

                    // Press item to show context menu
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.showContextMenu();
                        }
                    });

                    return view;
                }
            };

            // Set the adapter to the ListView
            listViewHabits.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load habits.", Toast.LENGTH_SHORT).show();
        }
    }


    /*
     * FUNCTION      : onCreateOptionsMenu
     * DESCRIPTION   : This function creates the options menu in the action bar.
     * PARAMETERS    : Menu menu
     * RETURNS       : boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewhabits, menu);
        return true;
    }


    /*
     * FUNCTION      : onOptionsItemSelected
     * DESCRIPTION   : This function handles the action bar item selection.
     * PARAMETERS    : MenuItem item
     * RETURNS       : boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    /*
     * FUNCTION      : onCreateContextMenu
     * DESCRIPTION   : This function creates the context menu for the list view items.
     * PARAMETERS    : ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo
     * RETURNS       : void
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.habit_contextmenu, menu);
    }


    /*
     * FUNCTION      : onContextItemSelected
     * DESCRIPTION   : This function handles the context menu item selection.
     * PARAMETERS    : MenuItem item
     * RETURNS       : boolean
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.habit_contextmenu_delete) {
            assert info != null;
            showDeleteConfirmationDialog(info.position);
            return true;
        }

        return super.onContextItemSelected(item);
    }


    /*
     * FUNCTION      : showDeleteConfirmationDialog
     * DESCRIPTION   : This function shows a confirmation dialog before deleting a habit.
     * PARAMETERS    : int position
     * RETURNS       : void
     */
    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Habit");
        builder.setMessage("Are you sure you want to delete this habit?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(position);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


    /*
     * FUNCTION      : deleteItem
     * DESCRIPTION   : This function deletes a habit from the list and the database.
     * PARAMETERS    : int position
     * RETURNS       : void
     */
    private void deleteItem(int position) {
        Habit habit = habitList.get(position);
        habit.deleteFromDatabase(dbHelper.getWritableDatabase());
        habitList.remove(position);
        adapter.notifyDataSetChanged();
    }
}
