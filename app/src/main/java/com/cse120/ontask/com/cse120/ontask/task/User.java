package com.cse120.ontask.com.cse120.ontask.task;

import java.util.Vector;
import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = 6879876854546815L;

    private FullName name;
    private UserId id;
    private Vector<Task> taskList;
    private Vector<Project> projectList;
    private Vector<User> friendList;

    public User(FullName name){
        this.name = name;
        id = new UserId();
        taskList = new Vector<Task>();
        projectList = new Vector<Project>();
        friendList = new Vector<User>();
    }

    public User(String firstName, String lastName){
        this(new FullName(firstName, lastName));
    }

    public User(){
        this(new FullName());
    }

    // Getters
    public FullName GetName(){
        return name;
    }
    public UserId GetId(){
        return id;
    }
    public Vector<Task> GetTaskList(){
        return (Vector<Task>) taskList;
    }
    public Vector<Project> GetProjectList(){
        return (Vector<Project>) projectList;
    }
    public Vector<User> GetFriendList(){
        return (Vector<User>) friendList;
    }
    // End Getters

    // Setters
    public void SetName(FullName name){
        this.name = name;
    }
    public void SetTaskList(Vector<Task> taskList){
        this.taskList = taskList;
    }
    public void SetProjectList(Vector<Project> projectList){
        this.projectList = projectList;
    }
    public void SetUserList(Vector<User> friendList){
        this.friendList = friendList;
    }
    // End Setters
}