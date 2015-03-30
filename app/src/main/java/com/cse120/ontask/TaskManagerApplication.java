package com.cse120.ontask;

import com.cse120.ontask.com.cse120.ontask.task.Task;
import java.util.*;
import android.app.Application;


public class TaskManagerApplication extends Application {

    private ArrayList<Task> currentTasks;

    @Override
    public void onCreate() {
        super.onCreate();
        if (null == currentTasks) {
            currentTasks = new ArrayList<Task>();
        }
    }

    public void setCurrentTasks(ArrayList<Task> currentTasks) {
        this.currentTasks = currentTasks;
    }

    public ArrayList<Task> getCurrentTasks() {
        return currentTasks;
    }

    public void addTask(Task t) {
        assert (null != t);
        if (null == currentTasks) {
            currentTasks = new ArrayList<Task>();
        }
        else
            currentTasks.add(t);
    }
}
