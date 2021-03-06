package com.cse120.ontask;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class LogIn extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        if the user logged on previously
        skip log in page and go straight to
        home page (need to implement)
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    //Check input from user and login
    public void LogInButtonOnClick(View view){
        Intent i = new Intent(this, HomeActivity.class);

        //retrieve input
        EditText email = (EditText) findViewById(R.id.emailLogin);
        EditText password = (EditText) findViewById(R.id.passwordLogin);

        //check input with registered users (need to implement)

        //go to home activity
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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
