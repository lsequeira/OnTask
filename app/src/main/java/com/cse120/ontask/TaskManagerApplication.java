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

    @Override
    public void onCreate() {
        super.onCreate();

        DBHandler handler = new DBHandler(this, null, null, 1);

        /*--------------Load Tasks--------------*/
        currentTasks = handler.loadTasks(false);
        if (currentTasks == null) {
            currentTasks = new ArrayList<Task>();
        }

        completedTasks = handler.loadTasks(true);
        if (completedTasks == null) {
                completedTasks = new ArrayList<Task>();
        }

        /*--------------Load Projects--------------*/
        currentProjects = handler.loadProjects(false);
        if (currentProjects == null) {
            currentProjects = new ArrayList<Project>();
        }

        completedProjects = handler.loadProjects(true);
        if (completedProjects == null) {
            completedProjects = new ArrayList<Project>();
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

        //Add to correct List
        if (project.getIsCompleted()) {
            completedProjects.add(project);
        }
        else {
            currentProjects.add(project);
        }

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
        handler.updateTask(currentTasks.get(taskListIndex));
        handler.close();
    }

    public void updateProject(Project p, int listIndex){
        //Update task information
        currentProjects.get(listIndex).setTitle(p.getTitle());
        currentProjects.get(listIndex).setDescription(p.getDescription());
        currentProjects.get(listIndex).setUrgency(p.getUrgency());
        currentProjects.get(listIndex).setDeadline(p.getDeadline());
        currentProjects.get(listIndex).setIsCompleted(p.getIsCompleted());

        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.updateProject(currentProjects.get(listIndex));
        handler.close();
    }

    public void deleteTask(Task t, int taskListIndex){
        currentTasks.remove(taskListIndex);
        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.deleteTask(t);
        handler.close();
    }

    public void deleteProject(Project p, int listIndex){
        currentProjects.remove(listIndex);
        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.deleteProject(p);
        handler.close();
    }
    //TODO:Delete projects from database
}
