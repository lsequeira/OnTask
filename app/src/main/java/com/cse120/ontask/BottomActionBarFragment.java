package com.cse120.ontask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

public class BottomActionBarFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_action_bar, container, false);
        return view;
    }

}
