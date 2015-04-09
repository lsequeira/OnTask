package com.cse120.ontask;

import com.cse120.ontask.com.cse120.ontask.task.Task;
import com.cse120.ontask.com.cse120.ontask.task.Date;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class AddTaskActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private int day, month, year, hour, minute;

    TextView displayDate;
    TextView displayTime;

    //    the suffix arrays and array lists
    ArrayList<Integer> arrayList_st = new ArrayList<Integer>();
    int[] array_st = {1, 21, 31};
    ArrayList<Integer> arrayList_nd = new ArrayList<Integer>();
    int[] array_nd = {2, 22};
    ArrayList<Integer> arrayList_rd = new ArrayList<Integer>();
    int[] array_rd = {3, 23};

    String[] monthsArray = {"January", "February", "March", "April"
            , "May", "June", "July", "August", "September"
            , "October", "November", "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initializeDateSuffixLists();
        initializeDateTime();

        //Update Date and Time Text Views
        displayDate = (TextView) findViewById(R.id.dateTextView);
        displayDate.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));

        updateTime(hour, minute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void datePickerButtonOnClick(View v) {
        DialogFragment myDatePickerFragment = new DatePickerFragment();
        myDatePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        String suffix = getSuffix(day);
        Toast.makeText(this, monthsArray[month] + " " +
                String.valueOf(day) + suffix + " "
                        + String.valueOf(year), Toast.LENGTH_SHORT).show();
        //Initialize the date attributes
        setDate(day, month, year);
        displayDate.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
    }

    public void timePickerButtonOnClick(View v) {
        //Need to Get The User Info...
        DialogFragment myTimePickerFragment = new TimePickerFragment();
        myTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {
        updateTime(hour, minute);
        Toast.makeText(this, new StringBuilder().append("Time chosen is ").append(displayTime.getText()), Toast.LENGTH_SHORT).show();
    }

    public void addTaskButtonOnClick(View v) {
        //Add Task Object to the List
        Task t = createTaskObject();
        getTaskManagerApplication().addTask(t);

        //Go to home screen after adding task
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    protected Task createTaskObject() {
        String taskName, taskDescription;

        //Task Name
        EditText titleInput = (EditText)findViewById(R.id.taskTitle);
        if (!isEmpty(titleInput)) {
            taskName = titleInput.getText().toString();
        }
        else
            taskName = "Untitled";

        //Task Description
        EditText descriptionInput = (EditText)findViewById(R.id.taskDescription);
        if (!isEmpty(descriptionInput)) {
            taskDescription = descriptionInput.getText().toString();
        }
        else
            taskDescription = "Blank Description";

        //Task Deadline
        Date deadline = new Date(year, month, day, hour, minute);

        //Create the Task Object
        Task t = new Task(taskName, taskDescription, deadline);

        return t;
    }

    public void cancelTaskButtonOnClick(View v) {
        //Returns to previous ChooseTaskOrProject Activity
        finish();
    }

    //Function to interact with the Task Manager Application
    private TaskManagerApplication getTaskManagerApplication() {
        TaskManagerApplication tma = (TaskManagerApplication) getApplication();
        return tma;
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    //Add padding to minutes
    private static String pad(int min) {
        if (min >= 10)
            return String.valueOf(min);
        else
            return "0" + String.valueOf(min);
    }

    //Begin Date Suffix Functions
    public void initializeDateSuffixLists() {
        //Initialize the lists with pre-defined arrays
        for (int i = 0; i < array_st.length; i++) {
            arrayList_st.add(array_st[i]);
        }

        for (int i = 0; i < array_nd.length; i++) {
            arrayList_nd.add(array_nd[i]);
        }

        for (int i = 0; i < array_rd.length; i++) {
            arrayList_rd.add(array_rd[i]);
        }
    }

    //Function used to determine the suffix of a given day
    private String getSuffix(int day) {
        String suffix = null;
        if (arrayList_st.contains(day)) {
            suffix = "st";
        } else if (arrayList_nd.contains(day)) {
            suffix = "nd";
        } else if (arrayList_rd.contains(day)) {
            suffix = "rd";
        } else {
            suffix = "th";
        }
        return suffix;
    }
    //End Date Suffix Functions

    //Begin Date Setters and Getters
    public void setDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getMinute() {
        return minute;
    }
    public int getHour() {
        return hour;
    }
    public int getDay() {
        return day;
    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    //End Date Setters and Getters

    public void initializeDateTime() {
        //Initialize Date and Time
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    public void updateTime(int hour, int minute) {
        int hour_ampm = hour%12;
        String am_pm;

        displayTime = (TextView)findViewById(R.id.timeTextView);

        //Find Whether it is AM or PM
        if (hour >= 12) {
            am_pm = "PM";
        }
        else {
            am_pm = "AM";
            //Ima add this comment so the brackets are not in vain
        }

        //Print correct 12-hour hour
        if (hour_ampm == 0) {
            hour_ampm = 12;
        }
        else {

        }

        displayTime.setText(new StringBuilder().append(hour_ampm).append(":").append(pad(minute)).append(" ").append(am_pm));
    }
}
