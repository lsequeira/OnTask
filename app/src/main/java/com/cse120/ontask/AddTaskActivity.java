package com.cse120.ontask;

import com.cse120.ontask.com.cse120.ontask.task.Task;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;


public class AddTaskActivity extends ActionBarActivity {

    private EditText titleInput;
    private EditText descriptionInput;
    private EditText dateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
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

    public void addTaskButtonOnClick(View v){
        //Add Task Object to the List
        Task t = createTaskObject();
        getTaskManagerApplication().addTask(t);

        //Go to home screen after adding task
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    protected Task createTaskObject() {
        //Task Name
        titleInput = (EditText)findViewById(R.id.taskTitle);
        String taskName = titleInput.getText().toString();

        //Task Description
        descriptionInput = (EditText)findViewById(R.id.taskDescription);
        String taskDescription = descriptionInput.getText().toString();

        //Task Deadline
        dateInput = (EditText)findViewById(R.id.taskDate);
        String taskDeadline = dateInput.getText().toString();

        Task t = new Task(taskName, taskDescription, taskDeadline);

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
}
