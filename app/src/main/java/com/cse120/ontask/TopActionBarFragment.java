package com.cse120.ontask;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopActionBarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopActionBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopActionBarFragment extends Fragment {

    TopActionBarListener activityCommander;

    public TopActionBarFragment() {
        // Required empty public constructor
    }

    interface TopActionBarListener{

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            activityCommander = (TopActionBarListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_action_bar, container, false);
    }



}
