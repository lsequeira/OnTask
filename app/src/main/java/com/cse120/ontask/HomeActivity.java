package com.cse120.ontask;


import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;


public class HomeActivity extends FragmentActivity implements BottomActionBarFragment.BottomActionBarListener,
        TopActionBarFragment.TopActionBarListener, TaskListFragment.OnFragmentInteractionListener {

    //changes as the bottom action bar buttons are pressed
    //reflects which list is currently displayed
    private TextView currentListDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //currentListDisplayed.setText("@string/allTaskList");
        setContentView(R.layout.activity_home);
    }

    //method for TaskListFragment
    //TODO:rename method with appropriate names
    public void onFragmentInteraction(String id){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public void addButtonOnClick(View v){
        Intent i = new Intent(this, ChooseTaskOrProject.class);
        startActivity(i);
    }

}


