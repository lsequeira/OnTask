package com.cse120.ontask;

import com.cse120.ontask.task_attributes.Project;
import com.cse120.ontask.task_attributes.Task;
import com.cse120.ontask.database.DBHandler;

import java.util.*;
import android.app.Application;

//TODO: decide whether to have a separate list for completed/deleted tasks/projects
public class TaskManagerApplication extends Application {

    public static List<Task> currentTasks;
    public static List<Task> completedTasks;
    public static List<Project> currentProjects;
    public static List<Project> completedProjects;

    public int taskMaxKey;
    public int projectMaxKey;

    @Override
    public void onCreate() {
        super.onCreate();

        DBHandler handler = new DBHandler(this, null, null, 1);

        /*--------------Load Tasks--------------*/
        //load current tasks from database to current task list
        if (currentTasks == null) {
            currentTasks = handler.loadTasks(false);
            if (currentTasks != null) {
                taskMaxKey = currentTasks.get(currentTasks.size()-1).getTask_id();
                System.out.println("DATABASE MAX KEY: " + taskMaxKey);
            }
            else {
                currentTasks = new ArrayList<Task>();
                taskMaxKey = -1;
            }
        }
        //load completed tasks
        if (completedTasks == null) {
            completedTasks = handler.loadTasks(true);
            if (completedTasks != null) {
                //taskMaxKey = currentTasks.get(currentTasks.size()-1).getTask_id();
            }
            else {
                completedTasks = new ArrayList<Task>();
                //taskMaxKey = -1;
            }
        }

        //TODO:if completed tasks work do same for completed projects
        /*--------------Load Projects--------------*/
        if (currentProjects == null) {
            currentProjects = handler.loadProjects(false);
            if (currentProjects != null) {
                projectMaxKey = currentProjects.get(currentProjects.size()-1).getProject_key();
            }
            else {
                currentProjects = new ArrayList<Project>();
                taskMaxKey = -1;
            }
        }
        if (completedProjects == null) {
            completedProjects = handler.loadProjects(true);
            if (completedProjects != null) {
                //taskMaxKey = currentTasks.get(currentTasks.size()-1).getTask_id();
            }
            else {
                completedProjects = new ArrayList<Project>();
                //taskMaxKey = -1;
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

    public List<Project> getCurrentProjects() {
        return currentProjects;
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public List<Project> getCompletedProjects() {
        return completedProjects;
    }

    public void addTask(Task task) {
        DBHandler handler = new DBHandler(this, null, null, 1);

        //Add to correct List
        if(task.getIsCompleted()) {
            completedTasks.add(task);
        }
        else{
            currentTasks.add(task);
        }

        //Add To Database
        handler.addTask(task);

        handler.close();
    }

    public void addProject(Project project) {
        DBHandler handler = new DBHandler(this, null, null, 1);

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
        currentTasks.get(taskListIndex).setDeadline(t.getDeadline());
        currentTasks.get(taskListIndex).setIsCompleted(t.getIsCompleted());

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
    //TODO:Delete projects from database
}
