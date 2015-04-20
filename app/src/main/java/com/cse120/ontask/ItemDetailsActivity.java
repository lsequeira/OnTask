package com.cse120.ontask;


import com.cse120.ontask.database.DBHandler;
import com.cse120.ontask.task_attributes.Project;
import com.cse120.ontask.task_attributes.Task;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;

public class ItemDetailsActivity extends FragmentActivity {

    private Task taskDisplayed;
    private Project projectDisplayed;
    private int taskListIndex;
    private int listID;
    private boolean isTask;

    private TextView taskTitle;
    private TextView taskDescription;
    private TextView taskDeadline;
    private TextView taskUrgency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //Set Text Views
        taskTitle = (TextView) findViewById(R.id.itemTitle);
        taskDescription = (TextView) findViewById(R.id.itemDescription);
        taskDeadline = (TextView) findViewById(R.id.itemDeadline);
        taskUrgency = (TextView) findViewById(R.id.itemUrgency);

        Bundle taskData = getIntent().getExtras();
        if(taskData == null) {
            return;
        }

        taskListIndex = taskData.getInt("taskSelected");
        listID = taskData.getInt("listID");
        isTask = false;

        //Get Corresponding List
        switch (listID) {
            case 0:
                taskDisplayed = TaskManagerApplication.currentTasks.get(taskListIndex);
                isTask = true;
                break;
            case 1:
                projectDisplayed = TaskManagerApplication.currentProjects.get(taskListIndex);
                break;
            case 2:
                taskDisplayed = TaskManagerApplication.completedTasks.get(taskListIndex);
                isTask = true;
                break;
            case 3:
                projectDisplayed = TaskManagerApplication.completedProjects.get(taskListIndex);
                break;
            default:
                break;
        }

        if(isTask) {
            taskSetView();
        }
        else {
            setProjectView();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_details, menu);
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

    public void UpdateButtonOnClick(View v){
        Intent i = new Intent(this, AddItemActivity.class);
        boolean isUpdating = true;
        i.putExtra("isUpdating", isUpdating);
        //TODO:Refactor the update
        i.putExtra("taskToUpdate", taskListIndex);
        startActivity(i);
    }

    public void CompletedButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        TaskManagerApplication app = new TaskManagerApplication();
        Task t;
        //add current task to completed list and remove from current list
        app.getCurrentTasks().get(taskListIndex).setIsCompleted(true);
        t = app.getCurrentTasks().get(taskListIndex);
        app.getCompletedTasks().add(t);
        app.getCurrentTasks().remove(taskListIndex);

        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.updateTask(t);
        handler.close();

        startActivity(i);
    }

    public void BackButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        //TODO:Change list view on back button
        startActivity(i);
    }

    public void DeleteButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        //TODO:Create a template to switch between task and project list - change variable names too
        if (isTask) {
            getTaskManagerApplication().deleteTask(taskDisplayed, taskListIndex);
        }
        else {
        //TODO:Delete Projects from database
            //getTaskManagerApplication().deleteTask(projectDisplayed, taskListIndex);
        }
        startActivity(i);
    }

    private TaskManagerApplication getTaskManagerApplication() {
        TaskManagerApplication tma = (TaskManagerApplication) getApplication();
        return tma;
    }

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
    private static String pad(int min) {
        if (min >= 10)
            return String.valueOf(min);
        else
            return "0" + String.valueOf(min);
    }

    public void taskSetView() {
        taskTitle.setText(taskDisplayed.getTitle());
        taskDescription.setText(taskDisplayed.getDescription());

        String urgency = "no urgency specified";
        switch (taskDisplayed.getUrgency()) {
            case LOWEST:
                urgency = "Very Low";
                break;
            case LOW:
                urgency = "Low";
                break;
            case MEDIUM:
                urgency = "Moderate";
                break;
            case HIGH:
                urgency = "High";
                break;
            case HIGHEST:
                urgency = "Very High";
                break;
            default:
                break;
        }

        taskUrgency.setText(urgency);
        taskDeadline.setText(
                Integer.toString(taskDisplayed.getDeadline().getMonth()) + "/" +
                        Integer.toString(taskDisplayed.getDeadline().getDay()) + "/" +
                        Integer.toString(taskDisplayed.getDeadline().getYear()) +
                        "\t\t\t\t" + convertTime(taskDisplayed.getDeadline().getHour(), taskDisplayed.getDeadline().getMinute())
        );
    }

    public void setProjectView() {
        taskTitle.setText(projectDisplayed.getTitle());
        taskDescription.setText(projectDisplayed.getDescription());

        String urgency = "no urgency specified";
        switch (projectDisplayed.getUrgency()) {
            case LOWEST:
                urgency = "Very Low";
                break;
            case LOW:
                urgency = "Low";
                break;
            case MEDIUM:
                urgency = "Moderate";
                break;
            case HIGH:
                urgency = "High";
                break;
            case HIGHEST:
                urgency = "Very High";
                break;
            default:
                break;
        }

        taskUrgency.setText(urgency);
        taskDeadline.setText(
                Integer.toString(projectDisplayed.getDeadline().getMonth()) + "/" +
                        Integer.toString(projectDisplayed.getDeadline().getDay()) + "/" +
                        Integer.toString(projectDisplayed.getDeadline().getYear()) +
                        "\t\t\t\t" + convertTime(projectDisplayed.getDeadline().getHour(), projectDisplayed.getDeadline().getMinute())
        );
    }
}
