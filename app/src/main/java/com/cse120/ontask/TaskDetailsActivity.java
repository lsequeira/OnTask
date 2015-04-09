package com.cse120.ontask;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import com.cse120.ontask.com.cse120.ontask.task.Task;

import org.w3c.dom.Text;

public class TaskDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Bundle taskData = getIntent().getExtras();
        if(taskData == null){
            return;
        }

        Task taskDisplayed = (Task) taskData.getSerializable("taskSelected");

        TextView taskTitle = (TextView) findViewById(R.id.taskTitle);
        TextView taskDescription = (TextView) findViewById(R.id.taskDescription);
        TextView taskDeadline = (TextView) findViewById(R.id.taskDeadline);
        TextView taskUrgency = (TextView) findViewById(R.id.taskUrgency);

        taskTitle.setText(taskDisplayed.getTitle());
        taskDescription.setText(taskDisplayed.getDescription());
        taskUrgency.setText(Integer.toString(taskDisplayed.getUrgency()));

        taskDeadline.setText(
                Integer.toString(taskDisplayed.getDeadline().GetDay()) + " / " +
                Integer.toString(taskDisplayed.getDeadline().GetMonth()) + " / " +
                Integer.toString(taskDisplayed.getDeadline().GetYear()) +
                "\n" +
                Integer.toString(taskDisplayed.getDeadline().GetHour()) + " : " +
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
}
