package com.cse120.ontask;

/**
 * Created by Spencer Hirasawa on 4/7/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.*;

import com.cse120.ontask.com.cse120.ontask.task.Task;

public class TaskListAdapter extends ArrayAdapter{

    public TaskListAdapter(Context context, List<Task> currentTasks) {
        super(context, R.layout.task_list_view, currentTasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater taskListInflater = LayoutInflater.from(getContext());
        View taskListView = taskListInflater.inflate(R.layout.task_list_view, parent, false);

        Task taskSelected = (Task) getItem(position);
        TextView taskTitle = (TextView) taskListView.findViewById(R.id.taskTitle);
        //ImageView taskUrgency = (ImageView) taskListView.findViewById(R.id.urgencyImage);

        taskTitle.setText(taskSelected.getTitle());
        //taskUrgency.setImageDrawable(R.drawable.);
        return  taskListView;
    }
}
