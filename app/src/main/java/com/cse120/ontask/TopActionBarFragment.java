package com.cse120.ontask;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopActionBarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopActionBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopActionBarFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    View view;

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
        int spinnerPos = 0;
        boolean isHomeView = true;
        boolean isFriendsView = false;
        boolean isRequestsView = false;
        String projectTitle = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            spinnerPos = bundle.getInt("SpinnerView");
            isHomeView = bundle.getBoolean("isHomeView");
            //isFriendsView = bundle.getBoolean("isFriendsView");
            isRequestsView = bundle.getBoolean("isRequestsView");
            projectTitle = bundle.getString("projectTitle");
        }
        else{

        }

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_top_action_bar, container, false);

        if(isRequestsView) {
            TextView titleText = (TextView) view.findViewById(R.id.projectTitle);
            titleText.setText("Requests");
            hideHomeProjectWidgets();
        }
        else if(isHomeView) {
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
            spinner.setSelection(spinnerPos);

            hideProjectWidgets();
            System.out.println("chk hiding project widgets");
        }
        else{
            TextView projTitleTextView = (TextView) view.findViewById(R.id.projectTitle);
            projTitleTextView.setText(projectTitle);
            hideHomeWidgets();
            System.out.println("chk hiding home widgets");
        }
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

    public void hideProjectWidgets() {
        com.cse120.ontask.custom_shapes.CircleButton backButton = (com.cse120.ontask.custom_shapes.CircleButton) view.findViewById(R.id.projectBackButton);
        backButton.setVisibility(View.GONE);

        TextView projTitle = (TextView) view.findViewById(R.id.projectTitle);
        projTitle.setVisibility(View.GONE);
    }

    public void hideHomeWidgets(){
        Spinner spinner = (Spinner) view.findViewById(R.id.sortListSpinner);
        spinner.setVisibility(View.GONE);

        ImageButton navButton = (ImageButton) view.findViewById(R.id.navButton) ;
        navButton.setVisibility(View.GONE);
    }

    public void hideHomeProjectWidgets(){
        com.cse120.ontask.custom_shapes.CircleButton backButton = (com.cse120.ontask.custom_shapes.CircleButton) view.findViewById(R.id.projectBackButton);
        Spinner spinner = (Spinner) view.findViewById(R.id.sortListSpinner);
        spinner.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        ImageButton navButton = (ImageButton) view.findViewById(R.id.navButton) ;
        navButton.setVisibility(View.GONE);
    }
}
