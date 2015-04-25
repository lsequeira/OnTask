package com.cse120.ontask;

import com.cse120.ontask.task_attributes.Project;
import com.cse120.ontask.task_attributes.Task;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
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
    private TextView itemTopBar;

    private Button itemUpdate;
    private Button itemDelete;
    private Button itemComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //Set Text Views
        itemTitle = (TextView) findViewById(R.id.itemTitle);
        itemDescription = (TextView) findViewById(R.id.itemDescription);
        itemDeadline = (TextView) findViewById(R.id.itemDeadline);
        itemUrgency = (TextView) findViewById(R.id.itemUrgency);
        itemTopBar = (TextView) findViewById(R.id.topActionBarTitle);

        itemUpdate = (Button) findViewById(R.id.updateButton);
        itemDelete = (Button) findViewById(R.id.deleteButton);
        itemComplete = (Button) findViewById(R.id.completeButton);

        Bundle itemData = getIntent().getExtras();
        if(itemData == null) {
            return;
        }

        listIndex = itemData.getInt("taskSelected");
        listID = itemData.getInt("listID");
        isTask = false;

        //Get Corresponding List
        switch (listID) {
            case 0:
                itemDisplayed = TaskManagerApplication.currentTasks.get(listIndex);
                isTask = true;
                break;
            case 1:
                itemDisplayed = TaskManagerApplication.currentProjects.get(listIndex);
                break;
            case 2:
                itemDisplayed = TaskManagerApplication.completedTasks.get(listIndex);
                hideItemButtons();
                isTask = true;
                break;
            case 3:
                itemDisplayed = TaskManagerApplication.completedProjects.get(listIndex);
                hideItemButtons();
                break;
            default:
                break;
        }

        setView();
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
            System.out.println("Yes this is a project");
            i.putExtra("isProject", true);
        }
        startActivity(i);
    }

    public void CompletedButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);

        //add current task to completed list and remove from current list
        if(isTask) {
            Task t;
            getTaskManagerApplication().getCurrentTasks().get(listIndex).setIsCompleted(true);
            t = getTaskManagerApplication().getCurrentTasks().get(listIndex);
            getTaskManagerApplication().getCompletedTasks().add(t);
            getTaskManagerApplication().updateTask(t, listIndex);
            getTaskManagerApplication().getCurrentTasks().remove(listIndex);
        }
        else {
            Project p;
            getTaskManagerApplication().getCurrentProjects().get(listIndex).setIsCompleted(true);
            p = getTaskManagerApplication().getCurrentProjects().get(listIndex);
            getTaskManagerApplication().getCompletedProjects().add(p);
            getTaskManagerApplication().updateProject(p, listIndex);
            getTaskManagerApplication().getCurrentProjects().remove(listIndex);
        }

        startActivity(i);
    }

    public void BackButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("SpinnerView", listID);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void DeleteButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        if (isTask) {
            getTaskManagerApplication().deleteTask(itemDisplayed, listIndex);
        }
        else {
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

    public void setView() {
        if (!isTask) {
            itemTopBar.setText("Project Details");
        }
        else {
            itemTopBar.setText("Task Details");
        }
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

    public void hideItemButtons() {
        itemUpdate.setVisibility(View.GONE);
        itemDelete.setVisibility(View.GONE);
        itemComplete.setVisibility(View.GONE);
    }
}
