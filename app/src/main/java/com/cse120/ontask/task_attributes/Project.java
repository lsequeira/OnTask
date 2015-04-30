package com.cse120.ontask.task_attributes;

import java.util.ArrayList;
import java.util.List;

public class Project extends Task {
    private int project_id;
    private int projectAutoIncKey;
    private List<Task> taskList;
   /* private String title;
    private String description;
    private Urgency urgency;
    private Date deadline;
    private Vector<User> memberList;
    private Vector<User> adminList;

    private boolean isComplete;
*/
    public Project(String title, String description, Date deadline,
                   Urgency urgency, boolean isCompleted){
        //this.projectAutoIncKey = project_key;
        this.title = title;
        this.description = description;
        //this.frequency = frequency;
        this.urgency = urgency;
        this.deadline = deadline;
        //memberList = new Vector<User>();
        //adminList = new Vector<User>();
        //this.taskList = taskList;
        this.isComplete = isCompleted;
        taskList = new ArrayList<Task>();
    }

    public Project(int project_key, String title, int project_id, String description, Date deadline,
                   Urgency urgency, boolean isCompleted, String user_id){
        this.projectAutoIncKey = project_key;
        this.title = title;
        this.project_id = project_id;
        this.description = description;
        //this.frequency = frequency;
        this.urgency = urgency;
        this.deadline = deadline;
        //memberList = new Vector<User>();
        //adminList = new Vector<User>();
        //this.taskList = taskList;
        this.isComplete = isCompleted;
        this.user_id = user_id;
        taskList = new ArrayList<Task>();
    }

    public Project(){
        taskList = new ArrayList<Task>();
    }

    public void EditProject(Project project){

    }

    public void SortList(){

    }

    //Getters
    /*public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Urgency getUrgency(){
        return urgency;
    }
    public Date getDeadline(){
        return deadline;
    }
    public Vector<User> getMemberList(){
        return (Vector<User>) memberList;
    }
    public Vector<User> getAdminList(){
        return (Vector<User>) adminList;
    }*/
    public int getProjectAutoIncKey(){
        return projectAutoIncKey;
    }
    public int getProject_id(){
        return project_id;
    }
    /*public boolean getIsCompleted(){
        return isCompleted;
    }
    */
    public List<Task> getTaskList(){
        return taskList;
    }
    //End Getters

    //Setters
    /*public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setUrgency(Urgency urgency){
        this.urgency = urgency;
    }
    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }
    public void setMemberList(Vector<User> memberList){
        this.memberList = memberList;
    }
    public void setAdminList(Vector<User> adminList){
        this.adminList = adminList;
    }
    */
    public void setProjectAutoIncKey(int project_key){
        this.projectAutoIncKey = project_key;
    }
    public void setProject_id(int project_id){
        this.project_id = project_id;
    }
    /*
    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }
    */
    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
    }

    //End Setters
}
