package com.cse120.ontask;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;

import com.cse120.ontask.Activities.UpdateTaskActivity;
import com.cse120.ontask.com.cse120.ontask.task.Task;
import android.view.View;

import org.w3c.dom.Text;

public class TaskDetailsActivity extends ActionBarActivity {

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

        //taskDisplayed = (Task) taskData.getSerializable("taskSelected");
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
                Integer.toString(taskDisplayed.getDeadline().GetMonth()) + "/" +
                Integer.toString(taskDisplayed.getDeadline().GetDay()) + "/" +
                Integer.toString(taskDisplayed.getDeadline().GetYear()) +
                "\t\t\t\t" +
                Integer.toString(taskDisplayed.getDeadline().GetHour()) + ":" +
                Integer.toString((taskDisplayed.getDeadline().GetMinute()))
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
        Intent i = new Intent(this, UpdateTaskActivity.class);

        i.putExtra("taskToUpdate", taskListIndex);
        startActivity(i);
    }

    public void BackButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
