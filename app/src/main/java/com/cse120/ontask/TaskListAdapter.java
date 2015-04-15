package com.cse120.ontask;

/**
 * Created by Spencer Hirasawa on 4/7/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.*;

import com.cse120.ontask.task_attributes.Task;

import org.w3c.dom.Text;


public class TaskListAdapter extends ArrayAdapter{

    public static final String LOWEST_URGENCY_COLOR = "#12dbf9";
    public static final String LOW_URGENCY_COLOR = "#61f007";
    public static final String MEDIUM_URGENCY_COLOR = "#fbfd0c";
    public static final String HIGH_URGENCY_COLOR = "#ffb70a";
    public static final String HIGHEST_URGENCY_COLOR = "#fb1010";


    public TaskListAdapter(Context context, List<Task> currentTasks) {
        super(context, R.layout.task_list_view, currentTasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater taskListInflater = LayoutInflater.from(getContext());
        View taskListView = taskListInflater.inflate(R.layout.task_list_view, parent, false);

        Task taskSelected = (Task) getItem(position);
        TextView taskTitle = (TextView) taskListView.findViewById(R.id.taskTitle);
        TextView taskDeadline = (TextView) taskListView.findViewById(R.id.taskDeadline);
        TextView taskUrgency = (TextView) taskListView.findViewById(R.id.urgencyColor);

        //set urgency color based on task's urgency value
        switch (taskSelected.getUrgency()){
            case LOWEST:
                taskUrgency.setBackgroundColor(Color.parseColor(LOWEST_URGENCY_COLOR));
                break;
            case LOW:
                taskUrgency.setBackgroundColor(Color.parseColor(LOW_URGENCY_COLOR));
                break;
            case MEDIUM:
                taskUrgency.setBackgroundColor(Color.parseColor(MEDIUM_URGENCY_COLOR));
                break;
            case HIGH:
                taskUrgency.setBackgroundColor(Color.parseColor(HIGH_URGENCY_COLOR));
                break;
            case HIGHEST:
                taskUrgency.setBackgroundColor(Color.parseColor(HIGHEST_URGENCY_COLOR));
                break;
            default:
                break;
        }

        //set task title text
        taskTitle.setText(taskSelected.getTitle());

        //set task deadline text
        taskDeadline.setText(
                Integer.toString(taskSelected.getDeadline().getMonth()) + "/" +
                        Integer.toString(taskSelected.getDeadline().getDay()) + "/" +
                        Integer.toString(taskSelected.getDeadline().getYear()) +
                        "\t\t\t\t"+ convertTime(taskSelected.getDeadline().getHour(), taskSelected.getDeadline().getMinute())
        );
        return  taskListView;
    }

    public String convertTime(int hour, int minute) {
        int hour_ampm = hour%12;
        String am_pm;

        //Find Whether it is AM or PM
        if (hour >= 12) {
            am_pm = "PM";
        }
        else {
            am_pm = "AM";
            //Ima add this comment so the brackets are not in vain
        }

        //Print correct 12-hour hour
        if (hour_ampm == 0) {
            hour_ampm = 12;
        }
        else {

        }

        return String.valueOf(hour_ampm) + ":"+ pad(minute) + " " + am_pm;
    }

    private static String pad(int min) {
        if (min >= 10)
            return String.valueOf(min);
        else
            return "0" + String.valueOf(min);
    }
}
