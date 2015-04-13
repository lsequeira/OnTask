package com.cse120.ontask;

import com.cse120.ontask.com.cse120.ontask.task.Project;
import com.cse120.ontask.com.cse120.ontask.task.Task;
import com.cse120.ontask.database.DBHandler;

import java.util.*;
import android.app.Application;

public class TaskManagerApplication extends Application {

    public static List<Task> currentTasks;
    public static List<Project> currentProjects;
    public int taskMaxKey;
    public int projectMaxKey;

    @Override
    public void onCreate() {
        super.onCreate();

        DBHandler handler = new DBHandler(this, null, null, 1);

        //load tasks from database to lists
        if (currentTasks == null) {
            currentTasks = handler.loadTasks();
            if (currentTasks != null) {
                taskMaxKey = currentTasks.get(currentTasks.size()-1).getTask_id();
            }
            else {
                currentTasks = new ArrayList<Task>();
                taskMaxKey = -1;
            }
        }
        if (currentProjects == null) {
            currentProjects = handler.loadProjects();
            if (currentProjects != null) {
                projectMaxKey = currentProjects.get(currentProjects.size()-1).getProject_key();
            }
            else {
                currentProjects = new ArrayList<Project>();
                taskMaxKey = -1;
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

    public void addProject(Project project) {
        DBHandler handler = new DBHandler(this, null, null, 1);

        assert (null != project);
        //Add to List
        currentProjects.add(project);

        //Add To Database
        handler.addProject(project);
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
