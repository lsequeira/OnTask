package com.cse120.ontask;

import com.cse120.ontask.task_attributes.Project;
import com.cse120.ontask.task_attributes.Task;
import com.cse120.ontask.database.DBHandler;

import java.util.*;
import android.app.Application;

import com.parse.Parse;

//TODO: decide whether to have a separate list for completed/deleted tasks/projects
public class TaskManagerApplication extends Application {

    public static String appUserId;
    public static String appUserFirstName;
    public static String appUserLastName;

    public static List<Task> currentTasks;
    public static List<Task> completedTasks;
    public static List<Project> currentProjects;
    public static List<Project> completedProjects;

    @Override
    public void onCreate() {
        super.onCreate();

        DBHandler handler = new DBHandler(this, null, null, 1);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "YVyu6WVhuXingBQmhIC1lWyAewxYwduGjUmqpjSO", "jpfyfJLhABH9AofKw14mktfqKSceHiBkEIcbEas1");

        /*--------------Load Tasks--------------*/
        currentTasks = handler.loadTasks(false, false, -1);
        if (currentTasks == null) {
            currentTasks = new ArrayList<Task>();
        }

        completedTasks = handler.loadTasks(true, false, -1);
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

    public void setAppUserId(String appUserId) {
        TaskManagerApplication.appUserId = appUserId;
    }

    public void setAppUserFirstName(String firstName) {
        TaskManagerApplication.appUserFirstName = firstName;
    }

    public void setAppUserLastName(String lastName) {
        TaskManagerApplication.appUserLastName = lastName;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public String getAppUserFirstName() {
        return appUserFirstName;
    }

    public String getAppUserLastName() {
        return appUserLastName;
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
        else if(task.getTaskProject_id() == -1 && !task.getIsCompleted()){
            currentTasks.add(task);
        }

        System.out.println("TaskManager 80 taskprojid: " +task.getTaskProject_id());
        //Add To Database
        handler.addTask(this, task);

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
        handler.addProject(this, project);

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

    public void updateProjectTask(Task t, int listIndex, int parentProjectIndex){
        currentProjects.get(parentProjectIndex).getTaskList().get(listIndex).setTitle(t.getTitle());
        currentProjects.get(parentProjectIndex).getTaskList().get(listIndex).setDescription(t.getDescription());
        currentProjects.get(parentProjectIndex).getTaskList().get(listIndex).setUrgency(t.getUrgency());
        currentProjects.get(parentProjectIndex).getTaskList().get(listIndex).setDeadline(t.getDeadline());
        currentProjects.get(parentProjectIndex).getTaskList().get(listIndex).setIsCompleted(t.getIsCompleted());

        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.updateTask(currentProjects.get(parentProjectIndex).getTaskList().get(listIndex));
        handler.close();
    }

    public void deleteTask(Task t, int taskListIndex){
        currentTasks.remove(taskListIndex);
        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.deleteTask(t);
        handler.close();
    }

    public void deleteProject(Project p, int listIndex){
        int taskListIndex = 0;
        Task taskToDelete;
        DBHandler handler = new DBHandler(this, null, null, 1);
        while(currentProjects.get(listIndex).getTaskList().size() > 0){
            taskToDelete = currentProjects.get(listIndex).getTaskList().get(taskListIndex);
            currentProjects.get(listIndex).getTaskList().remove(taskListIndex);
            handler.deleteTask(taskToDelete);
            //taskListIndex++;
        }
        currentProjects.remove(listIndex);
        handler.deleteProject(p);
        handler.close();
    }

    public void deleteProjectTask(Task t, int listIndex, int parentProjectIndex) {
        currentProjects.get(parentProjectIndex).getTaskList().remove(listIndex);

        DBHandler handler = new DBHandler(this, null, null, 1);
        handler.deleteTask(t);
        handler.close();
    }
}
