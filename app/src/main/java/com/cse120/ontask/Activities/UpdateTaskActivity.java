package com.cse120.ontask.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;

import com.cse120.ontask.R;
import com.cse120.ontask.TaskDetailsActivity;
import com.cse120.ontask.TaskManagerApplication;
import com.cse120.ontask.com.cse120.ontask.task.Task;

/*TODO:access the task object directly in order to change the value
*/

public class UpdateTaskActivity extends ActionBarActivity {

    Task taskDisplayed;
    int taskListindex;
    EditText taskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        Bundle taskData = getIntent().getExtras();
        if(taskData == null){
            return;
        }

        taskListindex = taskData.getInt("taskToUpdate");
        taskDisplayed = TaskManagerApplication.currentTasks.get(taskListindex);

        taskTitle = (EditText) findViewById(R.id.taskTitle);
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
        getMenuInflater().inflate(R.menu.menu_update_task, menu);
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


    public void CancelButtonOnClick(View v){
        Intent i = new Intent(this, TaskDetailsActivity.class);
        i.putExtra("taskSelected", taskListindex);
        startActivity(i);
    }

    public void SubmitChangesButtonOnClick(View v){
        Intent i = new Intent(this, TaskDetailsActivity.class);

        TaskManagerApplication.currentTasks.get(taskListindex).setTitle(taskTitle.getText().toString());

        i.putExtra("taskSelected", taskDisplayed);
        startActivity(i);
    }
}
