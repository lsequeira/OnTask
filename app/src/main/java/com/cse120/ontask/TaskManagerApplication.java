package com.cse120.ontask;

import com.cse120.ontask.com.cse120.ontask.task.Task;
import com.cse120.ontask.database.DBHandler;

import java.util.*;
import android.app.Application;

public class TaskManagerApplication extends Application {

    public static List<Task> currentTasks;
    public static Map<String, Task> currentTasks_map = new HashMap<String, Task>();
    public int maxKey;

    @Override
    public void onCreate() {
        super.onCreate();

        DBHandler handler = new DBHandler(this, null, null, 1);

        if (currentTasks == null) {
            currentTasks = handler.loadTasks();
            if (currentTasks != null) {
                //TODO: when adding tasks start from maxKey to assign task id
                maxKey = currentTasks.get(currentTasks.size()-1).getTask_id();
            }
            else {
                currentTasks = new ArrayList<Task>();
                maxKey = -1;
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

    public void updateTask(Task t, int taskListIndex){
        //Update task information
        currentTasks.get(taskListIndex).setTitle(t.getTitle());
        currentTasks.get(taskListIndex).setDescription(t.getDescription());
        currentTasks.get(taskListIndex).setUrgency(t.getUrgency());
        currentTasks.get(taskListIndex).setFrequency(t.getFrequency());
        currentTasks.get(taskListIndex).setDeadline(t.getDeadline());

        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.updateTask(t);
        handler.close();
    }

    public void deleteTask(Task t, int taskListIndex){
        currentTasks.remove(taskListIndex);
        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.deleteTask(t);
        handler.close();
    }
}
