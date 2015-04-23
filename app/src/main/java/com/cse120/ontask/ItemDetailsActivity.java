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

    private Task itemDisplayed;
    private int listIndex;
    private int listID;
    private boolean isTask;

    private TextView itemTitle;
    private TextView itemDescription;
    private TextView itemDeadline;
    private TextView itemUrgency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //Set Text Views
        itemTitle = (TextView) findViewById(R.id.itemTitle);
        itemDescription = (TextView) findViewById(R.id.itemDescription);
        itemDeadline = (TextView) findViewById(R.id.itemDeadline);
        itemUrgency = (TextView) findViewById(R.id.itemUrgency);

        Bundle taskData = getIntent().getExtras();
        if(taskData == null) {
            return;
        }

        listIndex = taskData.getInt("taskSelected");
        listID = taskData.getInt("listID");
        isTask = false;

        //Get Corresponding List
        switch (listID) {
            case 0:
                itemDisplayed = TaskManagerApplication.currentTasks.get(listIndex);
                isTask = true;
                break;
            case 1:
                itemDisplayed = new Project();
                itemDisplayed = TaskManagerApplication.currentProjects.get(listIndex);
                break;
            case 2:
                itemDisplayed = TaskManagerApplication.completedTasks.get(listIndex);
                isTask = true;
                break;
            case 3:
                itemDisplayed = TaskManagerApplication.completedProjects.get(listIndex);
                break;
            default:
                break;
        }

        SetView();
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
        i.putExtra("taskToUpdate", listIndex);
        i.putExtra("listID", listID);
        if(!isTask){
            i.putExtra("isProject", true);
        }
        startActivity(i);
    }

    public void CompletedButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        TaskManagerApplication app = new TaskManagerApplication();
        DBHandler handler = new DBHandler(this, null, null, 1);
        //add current task to completed list and remove from current list
        if(isTask) {
            Task t;
            app.getCurrentTasks().get(listIndex).setIsCompleted(true);
            t = app.getCurrentTasks().get(listIndex);
            app.getCompletedTasks().add(t);
            app.getCurrentTasks().remove(listIndex);
            handler.updateTask(t);
        }
        else {
            Project p;
            app.getCurrentProjects().get(listIndex).setIsCompleted(true);
            p = app.getCurrentProjects().get(listIndex);
            app.getCompletedProjects().add(p);
            app.getCurrentProjects().remove(listIndex);
            handler.updateProject(p);
        }

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
            getTaskManagerApplication().deleteTask(itemDisplayed, listIndex);
        }
        else {
        //TODO:Delete Projects from database
            getTaskManagerApplication().deleteProject((Project)itemDisplayed, listIndex);
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

    public void SetView() {
        itemTitle.setText(itemDisplayed.getTitle());
        itemDescription.setText(itemDisplayed.getDescription());

        String urgency = "no urgency specified";
        switch (itemDisplayed.getUrgency()) {
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

        itemUrgency.setText(urgency);
        itemDeadline.setText(
                Integer.toString(itemDisplayed.getDeadline().getMonth()) + "/" +
                        Integer.toString(itemDisplayed.getDeadline().getDay()) + "/" +
                        Integer.toString(itemDisplayed.getDeadline().getYear()) +
                        "\t\t\t\t" + convertTime(itemDisplayed.getDeadline().getHour(), itemDisplayed.getDeadline().getMinute())
        );
    }
}
