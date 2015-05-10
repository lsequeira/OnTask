package com.cse120.ontask;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;

public class LogInActivity extends FragmentActivity implements FBLoginFragment.OnFragmentInteractionListener{

    // Get Facebook Debug Hash Key
    /*
    public static void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.cse120.ontask", PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        if the user logged on previously
        skip log in page and go straight to
        home page (need to implement)
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //showHashKey(getApplicationContext());

        //Intent i = new Intent(this, HomeActivity.class);
        //startActivity(i);
    }

    //Check input from user and login
    public void LogInButtonOnClick(View view){
        Intent i = new Intent(this, HomeActivity.class);

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

    public void switchActivity(String appUserId, String appUserFirstName, String appUserLastName) {
        getTaskManagerApplication().setAppUserId(appUserId);
        getTaskManagerApplication().setAppUserFirstName(appUserFirstName);
        getTaskManagerApplication().setAppUserLastName(appUserLastName);

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void setFriends(ArrayList<String> friends) {
        getTaskManagerApplication().setFacebookFriends(friends);
    }

    private TaskManagerApplication getTaskManagerApplication() {
        TaskManagerApplication tma = (TaskManagerApplication) getApplication();
        return tma;
    }
}
