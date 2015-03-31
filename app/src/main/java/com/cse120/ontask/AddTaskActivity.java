package com.cse120.ontask;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.cse120.ontask.com.cse120.ontask.task.Task;

public class AddTaskActivity extends ActionBarActivity {

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
        Intent i = new Intent(this, HomeActivity.class);

        //TODO: pass input to Task constructor to create a task
        final EditText titleInput = (EditText) findViewById(R.id.taskTitle);
        final EditText descriptionInput = (EditText) findViewById(R.id.taskDescription);
        final EditText dateInput = (EditText) findViewById(R.id.taskDate);

        Toast.makeText(getApplicationContext(),"Created Task: " + titleInput.getText().toString() , Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    public void cancelTaskButtonOnClick(View v){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
