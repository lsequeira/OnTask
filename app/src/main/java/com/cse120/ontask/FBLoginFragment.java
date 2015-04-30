package com.cse120.ontask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cse120.ontask.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class FBLoginFragment extends Fragment {

    private static CallbackManager mCallbackManager;
    private static AccessTokenTracker mTokenTracker;
    private static ProfileTracker mProfileTracker;

    private static OnFragmentInteractionListener mListener;


    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            System.out.println("CHECK: success");

            AccessToken accessToken = loginResult.getAccessToken();
            //Profile profile = Profile.getCurrentProfile();

            //mListener.switchActivity();
        }

        @Override
        public void onCancel() {
            System.out.println("CHECK: cancel");
        }

        @Override
        public void onError(FacebookException e) {
            System.out.println("CHECK: error");
        }
    };

    public FBLoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if(newProfile != null) {
                    final String firstName = newProfile.getFirstName();
                    final String lastName = newProfile.getLastName();
                    final String id = newProfile.getId();

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");

                    query.whereEqualTo("userId", id);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> queryUsers, ParseException e) {
                            if (e == null) {
                                if (queryUsers.size() == 0) {
                                    ParseObject pUser = new ParseObject("Users");

                                    pUser.put("firstName", firstName);
                                    pUser.put("lastName", lastName);
                                    pUser.put("userId", id);
                                    pUser.put("userName", "username");

                                    pUser.pinInBackground();
                                    pUser.saveEventually();
                                }

                            } else {
                                Log.d("score", "Error: " + e.getMessage());
                            }
                        }
                    });

                    mListener.switchActivity(id, firstName, lastName);
                }
                else{

                }
            }
        };

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_fb_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mCallback);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (OnFragmentInteractionListener) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }

    @Override
    public void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    interface OnFragmentInteractionListener {
        public void switchActivity(String appUserId, String appUserFirstName, String appUserLastName);
    }
}
