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
import com.facebook.login.LoginManager;

import java.util.ArrayList;


public class HomeActivity extends FragmentActivity
        implements TopActionBarFragment.TopActionBarListener,
                   TaskListFragment.OnFragmentInteractionListener,
                   AdapterView.OnItemSelectedListener,
                   TopActionBarFragment.Callback,
                   FriendsListFragment.OnFragmentInteractionListener{

    //list IDs
    private static final int CURR_TASK = 0;
    private static final int CURR_PROJ = 1;
    private static final int COMP_TASK = 2;
    private static final int COMP_PROJ = 3;
    private static final int REQU_TASK = 4;
    private static final int NO_PROJECT_LIST_INDEX = -1;

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

    //Checks if Home view or Project view
    boolean isHomeView;
    boolean isFriendsView;
    boolean isRequestsView;

    private int projectListIndex;
    private int parentProjIndex;
    private String projectTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        projectListIndex = NO_PROJECT_LIST_INDEX;
        isHomeView = true;
        isFriendsView = false;
        isRequestsView = false;

        //Get Data from Bundle (if any)
        Intent intentExtras = getIntent();
        Bundle extras = intentExtras.getExtras();
        if(extras != null)
        {
            spinnerPos = extras.getInt("SpinnerView");
            if (extras.containsKey("projectListIndex")) {
                projectListIndex = extras.getInt("projectListIndex");
            }
            if (extras.containsKey("isHomeView")) {
                isHomeView = extras.getBoolean("isHomeView");
            }
            if (extras.containsKey("isFriendsView")) {
                isFriendsView = extras.getBoolean("isFriendsView");
            }
            if (extras.containsKey("isRequestsView")) {
                isRequestsView = extras.getBoolean("isRequestsView");
            }
            if(extras.containsKey("projectTitle")){
                projectTitle = extras.getString("projectTitle");
            }
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
            displayView(0, projectListIndex);
        }
    }

    @Override
    public void friendSelected(int friendListPosition) {

    }

    /**
     * Slide menu item click listener
     * */
    private class slideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position, NO_PROJECT_LIST_INDEX);
        }
    }

    /**
     * Displaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position, int projectListIndex) {
        // update the main content by replacing fragments
        TaskListFragment fragment = null;
        FriendsListFragment friendsListFragment = null;
        FragmentTransaction transaction;
        Bundle bundle;
        switch (position) {
            case 0:
                // Home
                fragment = new TaskListFragment();

                bundle = new Bundle();
                bundle.putInt("projectListIndex", projectListIndex);
                bundle.putInt("SpinnerView", spinnerPos);
                bundle.putBoolean("isHomeView", isHomeView);
                bundle.putBoolean("isFriendsView", isFriendsView);
                bundle.putBoolean("isRequestsView", isRequestsView);
                bundle.putString("projectTitle", projectTitle);
                fragment.setArguments(bundle);

                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment, "TaskListFragment");
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("");
                transaction.commit();
                break;
            case 1:
                // Friends List
                friendsListFragment = new FriendsListFragment();

                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, friendsListFragment, "FriendsListFragment");
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("");
                transaction.commit();
                break;
            case 2:
                // Requests
                fragment = new TaskListFragment();

                bundle = new Bundle();
                bundle.putInt("projectListIndex", projectListIndex);
                bundle.putInt("SpinnerView", 4);
                bundle.putBoolean("isHomeView", isHomeView);
                bundle.putBoolean("isFriendsView", isFriendsView);
                bundle.putBoolean("isRequestsView", true);
                bundle.putString("projectTitle", projectTitle);
                fragment.setArguments(bundle);

                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment, "TaskListFragment");
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("");
                transaction.commit();
                break;
            case 3:
                // Options

                break;
            case 4:
                // Logout
                LoginManager.getInstance().logOut();
                Intent i = new Intent(this, LogInActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }

        if (fragment != null || friendsListFragment != null) {
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
    public void onFragmentInteraction(int itemListIndex, int listID, boolean isProjTaskList, int parentProjectIndex, boolean isRequestsView){
        System.out.println("itemListIndex: " + itemListIndex + " listID: " + listID + " isProjTaskList " + isProjTaskList + " parentProjectIndex " + parentProjectIndex);

        Intent i;
        if(listID == CURR_TASK || listID == COMP_TASK || listID == REQU_TASK) {
            i = new Intent(this, ItemDetailsActivity.class);
            i.putExtra("itemSelected", itemListIndex);
            i.putExtra("listID", listID);
            i.putExtra("isProjTaskList", isProjTaskList);
            i.putExtra("parentProjectIndex", parentProjectIndex);

            startActivity(i);
        }
        else if ((listID == CURR_PROJ || listID == COMP_PROJ) && (isProjTaskList && !isRequestsView)) {
            parentProjIndex = itemListIndex;
            i = new Intent(this, ItemDetailsActivity.class);
            i.putExtra("itemSelected", itemListIndex);
            i.putExtra("listID", listID);
            i.putExtra("isProjTaskList", isProjTaskList);
            i.putExtra("parentProjectIndex", parentProjectIndex);

            System.out.println("Task inside project clicked!");
            startActivity(i);
        }
        else if (isRequestsView) {
            i = new Intent(this, ItemDetailsActivity.class);
            i.putExtra("itemSelected", itemListIndex);
            i.putExtra("listID", REQU_TASK);
            i.putExtra("isProjTaskList", isProjTaskList);
            i.putExtra("parentProjectIndex", parentProjectIndex);

            startActivity(i);
        }
        else{
            isHomeView = false;
            projectListIndex = itemListIndex;
            projectTitle = TaskManagerApplication.currentProjects.get(projectListIndex).getTitle();
            displayView(0, projectListIndex);
        }
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
        Intent i;
        if(isHomeView) {
            i = new Intent(this, ChooseTaskOrProject.class);
        }
        else{
            i = new Intent(this, AddItemActivity.class);
            i.putExtra("isProjectTask", true);
            i.putExtra("projectListIndex", projectListIndex);
            System.out.println("projectListIndex: " + projectListIndex + " parentProjectIndex: " + parentProjIndex);
        }
        startActivity(i);
    }

    public void projectBackButtonOnClick(View v){
        isHomeView = true;
        displayView(0, NO_PROJECT_LIST_INDEX);
    }

    public void projectTitleOnClick(View v){
        isHomeView = false;
        Intent i = new Intent(this, ItemDetailsActivity.class);
        i.putExtra("itemSelected",projectListIndex);
        i.putExtra("listID", CURR_PROJ);
        i.putExtra("isProjTaskList", false);
        startActivity(i);
    }

    public void navButtonOnClick(View v) {
        mDrawerLayout.openDrawer(mDrawerList);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //Get our dynamically created fragment by tag
        spinnerPos = pos;
        TaskListFragment fragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag("TaskListFragment");
        fragment.taskListView(pos);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}