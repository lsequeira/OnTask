package com.cse120.ontask;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopActionBarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopActionBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopActionBarFragment extends Fragment {

    public TopActionBarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_action_bar, container, false);
    }


}
