package com.cse120.ontask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
            Profile profile = Profile.getCurrentProfile();

            mListener.switchActivity();
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
                if(newProfile != null){
                   // mListener.switchActivity();
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
        public void switchActivity();
    }
}
