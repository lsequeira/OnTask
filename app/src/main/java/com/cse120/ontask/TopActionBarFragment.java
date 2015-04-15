package com.cse120.ontask;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopActionBarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopActionBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopActionBarFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public interface Callback {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id);
    }

    TopActionBarListener activityCommander;

    private Callback callback;

    public TopActionBarFragment() {
        // Required empty public constructor
    }

    interface TopActionBarListener{

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback) activity;

        try{
            activityCommander = (TopActionBarListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_action_bar, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.sortListSpinner);
        spinner.setOnItemSelectedListener(this);
        //spinner.setOnItemClickListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return view;
    }

    //Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (callback != null) {
            callback.onItemSelected(parent, view, pos, id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Another interface callback
    }
}
