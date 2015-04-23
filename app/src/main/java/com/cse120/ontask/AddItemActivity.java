package com.cse120.ontask;

import com.cse120.ontask.task_attributes.Task;
import com.cse120.ontask.task_attributes.Project;
import com.cse120.ontask.task_attributes.Date;
import com.cse120.ontask.task_attributes.Urgency;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Calendar;

public class AddItemActivity extends FragmentActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private int day, month, year, hour, minute;

    TextView displayDate;
    TextView displayTime;
    Urgency urgency;

    //    the suffix arrays and array lists
    ArrayList<Integer> arrayList_st = new ArrayList<Integer>();
    int[] array_st = {1, 21, 31};
    ArrayList<Integer> arrayList_nd = new ArrayList<Integer>();
    int[] array_nd = {2, 22};
    ArrayList<Integer> arrayList_rd = new ArrayList<Integer>();
    int[] array_rd = {3, 23};


    String[] monthsArray = { "January","February","March","April","May",
                "June","July","August","September","October","November","December" };

    //Used only for updating tasks
    boolean isUpdating;
    int taskListIndex;
    EditText taskTitle;
    EditText taskDescription;

    //Used only for adding projects
    boolean isProject;

    //Used only for adding project tasks
    boolean forProject;

    //Used to determine which list to update
    int listID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initializeDateSuffixLists();

        Bundle extraData = getIntent().getExtras();
        forProject = false;
        isProject = false;
        isUpdating = false;
        //if isUpdating then updating a task
        //else adding a task
        if(extraData == null){
            //Set the Date and Time TextViews to the current date/time
            initializeDateTime();
            //initialize urgency to LOWEST -- in case urgency buttons unchecked
            urgency = Urgency.LOWEST;
        }
        else if(extraData.getBoolean("isUpdating")) {
            //set all of the fields to the current task's information
            InitializeUpdate(extraData);
        }
        else if(extraData.getBoolean("isProject") && !extraData.getBoolean("isUpdating")){
            initializeDateTime();
            InitializeAddProject();
        }

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

    /* On-Click Functions */
    public void radioButtonOnClick(View v) {
        //Check to see if a button is checked
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.radio_lowest:
                if (checked) {
                    urgency = urgency.LOWEST;
                }
                break;
            case R.id.radio_low:
                if (checked) {
                    urgency = urgency.LOW;
                }
                break;
            case R.id.radio_medium:
                if (checked) {
                    urgency = urgency.MEDIUM;
                }
                break;
            case R.id.radio_high:
                if (checked) {
                    urgency = urgency.HIGH;
                }
                break;
            case R.id.radio_highest:
                if (checked) {
                    urgency = urgency.HIGHEST;
                }
                break;
            default:
                urgency = urgency.NONE;
                break;
        }
    }

    public void cancelTaskButtonOnClick(View v) {
        //Returns to previous ChooseTaskOrProject Activity
        finish();
    }

    public void addTaskButtonOnClick(View v) {
        Intent i;
        if(isUpdating){
            i = new Intent(this, ItemDetailsActivity.class);
            Task t;
            if(isProject){
                t = createProjectObject();
                getTaskManagerApplication().updateProject((Project) t, taskListIndex);
                System.out.println("chk proj update");
            }
            else {
                t = createTaskObject();
                getTaskManagerApplication().updateTask(t, taskListIndex);
            }

            i.putExtra("taskSelected", taskListIndex);
        }
        else if(isProject && !isUpdating){
            Project p = createProjectObject();
            getTaskManagerApplication().addProject(p);

            i = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            //Project
            bundle.putInt("SpinnerView", 1);
            i.putExtras(bundle);
        }
        else{
            //Add Task Object to the List
            Task t = createTaskObject();
            getTaskManagerApplication().addTask(t);

            //Go to home screen after adding task
            i = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            //Task
            bundle.putInt("SpinnerView", 0);
            i.putExtras(bundle);
        }
        i.putExtra("listID", listID);
        startActivity(i);
    }
    /* End On-Click Functions */

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

        //Set taskProject_id if it is part of a project
        int taskProject_id = -1;
        if(forProject){
            //Get extra intent data for project object position in list
            //then get the project id
            //taskProject_id = ProjectObject().getProject_id();
        }

        //Added activities default to !isComplete
        boolean isComplete = false;

        //Create the Task Object
        Task t = new Task(taskName, taskDescription, deadline, urgency, forProject, taskProject_id, isComplete);

        return t;
    }

    protected Project createProjectObject(){
        String projectName, projectDescription;
        int projectKey;

        //Name
        EditText titleInput = (EditText)findViewById(R.id.taskTitle);
        if (!isEmpty(titleInput)) {
            projectName = titleInput.getText().toString();
        }
        else
            projectName = "Untitled";

        //Description
        EditText descriptionInput = (EditText)findViewById(R.id.taskDescription);
        if (!isEmpty(descriptionInput)) {
            projectDescription = descriptionInput.getText().toString();
        }
        else
            projectDescription = "Blank Description";

        //Deadline
        Date deadline = new Date(year, month, day, hour, minute);


        //ID
        if (isUpdating) {
            projectKey = TaskManagerApplication.currentProjects.get(taskListIndex).getTaskAutoIncKey();
        }
        else {
            projectKey = getTaskManagerApplication().taskMaxKey;
        }

        //Added project is defaulted to !isComplete
        boolean isComplete = false;

        //Create the Task Object
        Project p = new Project(projectName, projectName, projectDescription, deadline, urgency, isComplete);

        return p;
    }

    //Function to interact with the Task Manager Application
    private TaskManagerApplication getTaskManagerApplication() {
        TaskManagerApplication tma = (TaskManagerApplication) getApplication();
        return tma;
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    /* Begin Date Picker Functionality */
    public void datePickerButtonOnClick(View v) {
        DialogFragment myDatePickerFragment = new DatePickerFragment();
        myDatePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        //Initialize the date attributes
        setDate(day, month+1, year);
        displayDate.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
        String suffix = getSuffix(day);
        Toast.makeText(this, monthsArray[month] + " " +
                String.valueOf(day) + suffix + " "
                + String.valueOf(year), Toast.LENGTH_SHORT).show();
    }

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
    /* End Date Picker Functionality */

    /* Begin Time Picker Functionality */
    public void timePickerButtonOnClick(View v) {
        //Need to Get The User Info...
        DialogFragment myTimePickerFragment = new TimePickerFragment();
        myTimePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {
        setTime(hour,minute);
        displayTime.setText(convertTime(hour,minute));
        Toast.makeText(this, new StringBuilder().append("Time chosen is ").append(convertTime(hour,minute)), Toast.LENGTH_SHORT).show();
    }

    //Convert from 24-hr to 12-hr format and display the TextView
    public String convertTime(int hour, int minute) {
        int hour_ampm = hour%12;
        String am_pm;

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
        return String.valueOf(hour_ampm) + ":"+ pad(minute) + " " + am_pm;
    }

    //Add padding to minutes
    private static String pad(int min) {
        if (min >= 10)
            return String.valueOf(min);
        else
            return "0" + String.valueOf(min);
    }
    /* End Time Picker Functionality */

    /* Begin Date and Time Initializer Functions */
    public void initializeDateTime() {
        //Initialize Date and Time
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH)+1;
        year = calendar.get(Calendar.YEAR);

        //Date
        displayDate = (TextView)findViewById(R.id.dateTextView);
        displayDate.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
        //Time
        displayTime = (TextView)findViewById(R.id.timeTextView);
        displayTime.setText(convertTime(hour,minute));
    }

    public void setDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public void setDateTime(int day, int month, int year, int hour, int minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }
    /* End Date and Time Initializer Functions */


    private void InitializeUpdate(Bundle updateData){
        isUpdating = updateData.getBoolean("isUpdating");
        taskListIndex = updateData.getInt("taskToUpdate");
        isProject = updateData.getBoolean("isProject");
        listID = updateData.getInt("listID");
        Task taskToUpdate;
        if(listID == 0) {
            taskToUpdate = TaskManagerApplication.currentTasks.get(taskListIndex);
        }
        else{
            taskToUpdate = TaskManagerApplication.currentProjects.get(taskListIndex);
        }

        TextView staticUpdateText = (TextView) findViewById(R.id.addTaskText);
        staticUpdateText.setText("Update Task");

        taskTitle = (EditText) findViewById(R.id.taskTitle);
        taskTitle.setText(taskToUpdate.getTitle());

        taskDescription = (EditText) findViewById(R.id.taskDescription);
        taskDescription.setText(taskToUpdate.getDescription());

        setDateTime(taskToUpdate.getDeadline().getDay(),taskToUpdate.getDeadline().getMonth(), taskToUpdate.getDeadline().getYear(),
                taskToUpdate.getDeadline().getHour(), taskToUpdate.getDeadline().getMinute());

        displayDate = (TextView) findViewById(R.id.dateTextView);
        displayDate.setText(new StringBuilder().append(taskToUpdate.getDeadline().getMonth()).append("/").append(taskToUpdate.getDeadline().getDay()).append("/").append(taskToUpdate.getDeadline().getYear()));

        displayTime = (TextView)findViewById(R.id.timeTextView);
        displayTime.setText(convertTime(taskToUpdate.getDeadline().getHour(),taskToUpdate.getDeadline().getMinute()));

        RadioButton taskUrgency;
        switch (taskToUpdate.getUrgency()){
            case LOWEST:
                taskUrgency = (RadioButton) findViewById(R.id.radio_lowest);
                taskUrgency.setChecked(true);
                urgency = Urgency.LOWEST;
                break;
            case LOW:
                taskUrgency = (RadioButton) findViewById(R.id.radio_low);
                taskUrgency.setChecked(true);
                urgency = Urgency.LOW;
                break;
            case MEDIUM:
                taskUrgency = (RadioButton) findViewById(R.id.radio_medium);
                taskUrgency.setChecked(true);
                urgency = Urgency.MEDIUM;
                break;
            case HIGH:
                taskUrgency = (RadioButton) findViewById(R.id.radio_high);
                taskUrgency.setChecked(true);
                urgency = Urgency.HIGH;
                break;
            case HIGHEST:
                taskUrgency = (RadioButton) findViewById(R.id.radio_highest);
                taskUrgency.setChecked(true);
                urgency = Urgency.HIGHEST;
                break;
            default:
                break;
        }

        Button submitButton = (Button) findViewById(R.id.addTaskButton);
        submitButton.setText("Submit Changes");
    }

    private void InitializeAddProject(){
        isProject = true;
        urgency = Urgency.LOWEST;
        TextView staticProjectText = (TextView) findViewById(R.id.addTaskText);
        staticProjectText.setText("Add Project");

        Button submitButton = (Button) findViewById(R.id.addTaskButton);
        submitButton.setText("Add Project");
    }
}