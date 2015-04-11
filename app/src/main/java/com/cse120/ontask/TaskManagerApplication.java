package com.cse120.ontask;

import com.cse120.ontask.com.cse120.ontask.task.Task;
import com.cse120.ontask.database.DBHandler;

import java.util.*;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


public class TaskManagerApplication extends Application {

    public static List<Task> currentTasks;
    public static Map<String, Task> currentTasks_map = new HashMap<String, Task>();

    @Override
    public void onCreate() {
        super.onCreate();
        DBHandler handler = new DBHandler(this, null, null, 1);

        if (currentTasks == null) {
            currentTasks = handler.loadTasks();
            if (currentTasks == null) {
                currentTasks = new ArrayList<Task>();
            }
        }
        handler.close();
    }

    public void setCurrentTasks(ArrayList<Task> currentTasks) {
        this.currentTasks = currentTasks;
    }

    public List<Task> getCurrentTasks() {
        return currentTasks;
    }

    public void addTask(Task task) {
        DBHandler handler = new DBHandler(this, null, null, 1);

        assert (null != task);
        //Add to List
        currentTasks.add(task);

        //Add To Database
        handler.addTask(task);
        handler.close();
    }
}
