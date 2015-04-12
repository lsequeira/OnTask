package com.cse120.ontask;

import com.cse120.ontask.com.cse120.ontask.task.Task;
import com.cse120.ontask.database.DBHandler;

import java.util.*;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TaskManagerApplication extends Application {

    public static List<Task> currentTasks;
    public static Map<String, Task> currentTasks_map = new HashMap<String, Task>();
    private static Context mContext;
    public int maxKey;

    @Override
    public void onCreate() {
        super.onCreate();

        this.mContext = this;

        DBHandler handler = new DBHandler(this, null, null, 1);

        if (currentTasks == null) {
            currentTasks = handler.loadTasks();
            if (currentTasks == null) {
                currentTasks = new ArrayList<Task>();
            }
        }
        handler.close();
        //TODO: when adding tasks start from maxKey to assign task id
        maxKey = currentTasks.get(currentTasks.size()-1).getTask_id();
    }

    public static Context getContext(){
        return mContext;
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

    public void updateTask(Task t, int taskListIndex){
        //Update task information
        TaskManagerApplication.currentTasks.get(taskListIndex).setTitle(t.getTitle());
        TaskManagerApplication.currentTasks.get(taskListIndex).setDescription(t.getDescription());
        TaskManagerApplication.currentTasks.get(taskListIndex).setUrgency(t.getUrgency());
        TaskManagerApplication.currentTasks.get(taskListIndex).setDeadline(t.getDeadline());

        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.updateTask(t);
        handler.close();
    }

    public void deleteTask(Task t, int taskListIndex){
        TaskManagerApplication.currentTasks.remove(taskListIndex);
        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.deleteTask(t);
        handler.close();
    }
}
