package com.cse120.ontask;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cse120.ontask.task_attributes.Project;
import com.cse120.ontask.task_attributes.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TaskListFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int CURR_TASK = 0;
    private static final int CURR_PROJ = 1;
    private static final int COMP_TASK = 2;
    private static final int COMP_PROJ = 3;
    private static final int UNSPECIFIED = -1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int listID;
    private boolean isProjTaskList;
    private int projectListIndex;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static TaskListFragment newInstance(String param1, String param2) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Set the adapter to retrieve the list of tasks from the TaskManagerApplication
        mAdapter = new TaskListAdapter(getActivity(), TaskManagerApplication.currentTasks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        //Initialize in case bundle is null
        projectListIndex = UNSPECIFIED;
        listID = CURR_TASK;
        isProjTaskList = false;
        boolean isHomeView = true;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            projectListIndex = bundle.getInt("projectListIndex");
            listID = bundle.getInt("SpinnerView");
            isHomeView = bundle.getBoolean("isHomeView");
        }

        TopActionBarFragment childFrag = new TopActionBarFragment();
        Bundle childBundle = new Bundle();
        childBundle.putInt("SpinnerView", listID);
        childBundle.putBoolean("isHomeView", isHomeView);
        childFrag.setArguments(childBundle);
        this.getChildFragmentManager().beginTransaction().add(R.id.content_parent,childFrag).commit();

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        // Set the adapter
        if (projectListIndex == UNSPECIFIED) {
            System.out.println("Fell in here");
            mListView.setAdapter(mAdapter);
        }
        else {
            isProjTaskList = true;
            projectTaskListView(projectListIndex, listID);
        }

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(position, listID, isProjTaskList, projectListIndex);
            //System.out.println(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int taskListIndex, int listID, boolean projTaskList, int parentProjectIndex);
    }

    //Filter the Task List here based on the Spinner
    public void taskListView(int filter) {
        switch (filter){
            case 0:
                mAdapter = new TaskListAdapter(getActivity(), TaskManagerApplication.currentTasks);
                listID = CURR_TASK;
                break;
            case 1:
                mAdapter = new ProjectListAdapter(getActivity(), TaskManagerApplication.currentProjects);
                listID = CURR_PROJ;
                break;
            case 2:
                mAdapter = new TaskListAdapter(getActivity(), TaskManagerApplication.completedTasks);
                listID = COMP_TASK;
                break;
            case 3:
                mAdapter = new ProjectListAdapter(getActivity(), TaskManagerApplication.completedProjects);
                listID = COMP_PROJ;
                break;
        }
        mListView.setAdapter(mAdapter);
    }

    //Load a project's task list
    public void projectTaskListView(int listIndex, int spinnerID){
        System.out.println("Project List Index :" + listIndex);
        System.out.println("Spinner Position: " + spinnerID);
        Project p;
        switch (spinnerID){
            case 1:
                p = TaskManagerApplication.currentProjects.get(listIndex);
                mAdapter = new TaskListAdapter(getActivity(), p.getTaskList());
                break;
            case 3:
                p = TaskManagerApplication.completedProjects.get(listIndex);
                mAdapter = new TaskListAdapter(getActivity(), p.getTaskList());
                break;
            default:
                break;
        }
        mListView.setAdapter(mAdapter);
    }
}
