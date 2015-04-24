package com.cse120.ontask;

import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cse120.ontask.sliding_menu.NavDrawerItem;
import com.cse120.ontask.sliding_menu.NavDrawerListAdapter;

import java.util.ArrayList;


public class HomeActivity extends FragmentActivity
        implements TopActionBarFragment.TopActionBarListener,
                   TaskListFragment.OnFragmentInteractionListener,
                   AdapterView.OnItemSelectedListener,
                   TopActionBarFragment.Callback {

    //changes as the bottom action bar buttons are pressed
    //reflects which list is currently displayed
    private TextView currentListDisplayed;

    //Navigation Drawer variables
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    private int spinnerPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        //Get Data from Bundle (if any)
        Intent intentExtras = getIntent();
        Bundle extras = intentExtras.getExtras();
        if(extras != null)
        {
            spinnerPos = extras.getInt("SpinnerView");
        }
        else {
            spinnerPos = 0;
        }

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Friends
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Requests
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "100"));
        // Settings
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Sign Out
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new slideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        if (savedInstanceState == null) {
            // Display Home Screen Initially
            displayView(0);
        }
    }

    /**
     * Slide menu item click listener
     * */
    private class slideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    /**
     * Displaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        TaskListFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TaskListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("SpinnerView", spinnerPos);
                fragment.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment, "TaskListFragment");
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("");
                transaction.commit();

                break;
            case 1:
                System.out.println("Choose 1st position");
                break;
            case 2:
                System.out.println("Choose 2nd position");
                break;
            case 3:
                System.out.println("Choose 3rd position");
                break;
            case 4:
                System.out.println("Choose 4th position");
                break;
            default:
                break;
        }

        if (fragment != null) {
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    //Method for TaskListFragment interaction
    public void onFragmentInteraction(int taskListIndex, int listID){
        //Task task = new Task(taskSelected.getTitle(),taskSelected.getDescription(),taskSelected.getDeadline());
        System.out.println("TaskListIndex: " + taskListIndex + "ListID: " + listID);
        Intent i = new Intent(this, ItemDetailsActivity.class);
        i.putExtra("taskSelected", taskListIndex);
        i.putExtra("listID", listID);

        startActivity(i);
       //Toast.makeText(this, taskSelected.getTitle(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
       // mDrawerToggle.syncState();
    }

    public void addButtonOnClick(View v){
        Intent i = new Intent(this, ChooseTaskOrProject.class);
        startActivity(i);
    }


    public void navButtonOnClick(View v) {
        mDrawerLayout.openDrawer(mDrawerList);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //Get our dynamically created fragment by tag
        TaskListFragment fragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag("TaskListFragment");
        fragment.taskListView(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}