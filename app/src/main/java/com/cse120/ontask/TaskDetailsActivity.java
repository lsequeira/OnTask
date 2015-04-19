package com.cse120.ontask;


import com.cse120.ontask.database.DBHandler;
import com.cse120.ontask.task_attributes.Task;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;

//TODO: need to specify which item is being displayed (task/proj)
//taskListIndex only refers to currentTask list causes indexing issues when dealing with other lists
public class TaskDetailsActivity extends FragmentActivity {

    Task taskDisplayed;
    int taskListIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Bundle taskData = getIntent().getExtras();
        if(taskData == null){
            return;
        }

        taskListIndex = taskData.getInt("taskSelected");

        taskDisplayed = TaskManagerApplication.currentTasks.get(taskListIndex);

        TextView taskTitle = (TextView) findViewById(R.id.taskTitle);
        TextView taskDescription = (TextView) findViewById(R.id.taskDescription);
        TextView taskDeadline = (TextView) findViewById(R.id.taskDeadline);
        TextView taskUrgency = (TextView) findViewById(R.id.taskUrgency);

        taskTitle.setText(taskDisplayed.getTitle());
        taskDescription.setText(taskDisplayed.getDescription());

        String urgency = "no urgency specified";
        switch (taskDisplayed.getUrgency()){
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_details, menu);
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
        Intent i = new Intent(this, AddTaskActivity.class);
        boolean isUpdating = true;
        i.putExtra("isUpdating", isUpdating);
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
        startActivity(i);
    }

    public void DeleteButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        System.out.println("DELETE BUTTON CLICK - TASK INDEX: " + taskListIndex);
        getTaskManagerApplication().deleteTask(taskDisplayed, taskListIndex);
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
}
