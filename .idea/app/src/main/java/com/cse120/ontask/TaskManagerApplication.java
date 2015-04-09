package com.cse120.ontask;

import com.cse120.ontask.com.cse120.ontask.task.Task;
import java.util.*;
import android.app.Application;


public class TaskManagerApplication extends Application {

    public static List<Task> currentTasks;
    public static Map<String, Task> currentTasks_map = new HashMap<String, Task>();

    @Override
    public void onCreate() {
        super.onCreate();
        if (currentTasks == null) {
            currentTasks = new ArrayList<Task>();
        }
    }

    public void setCurrentTasks(ArrayList<Task> currentTasks) {
        this.currentTasks = currentTasks;
    }

    public List<Task> getCurrentTasks() {
        return currentTasks;
    }

    public void addTask(Task t) {
        assert (null != t);

        currentTasks.add(t);
    }
}
