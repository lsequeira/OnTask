package com.cse120.ontask.com.cse120.ontask.task;

import java.util.Vector;
import java.util.*;

public class Project {
    private String project_id;
    private int project_key;
    private String title;
    private String description;
    private Frequency frequency;
    private Urgency urgency;
    private Date deadline;
    private Vector<User> memberList;
    private Vector<User> adminList;
    //private List<Task> taskList;

    public Project(int project_key, String title, String project_id, String description, Date deadline,
                   Urgency urgency){
        this.project_key = project_key;
        this.project_id = project_id;
        this.title = title;
        this.description = description;
        //this.frequency = frequency;
        this.urgency = urgency;
        this.deadline = deadline;
        memberList = new Vector<User>();
        adminList = new Vector<User>();
        //this.taskList = taskList;
    }

    /*public Project(){
        this("Blank Project", "Empty", Frequency.ONCE, Urgency.LOW, new Date(), new List<Task> );
    }*/

    public void EditProject(Project project){

    }

    public void SortList(){

    }

    //Getters
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Frequency getFrequency(){
        return frequency;
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
    }
    public int getProject_key(){
        return project_key;
    }
    public String getProject_id(){
        return project_id;
    }
    /*public Vector<Task> getTaskList(){
        return (Vector<Task>) taskList;
    }*/
    //End Getters

    //Setters
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setFrequency(Frequency frequency){
        this.frequency = frequency;
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
    public void setProject_key(int project_key){
        this.project_key = project_key;
    }
    public void setProject_id(String project_id){
        this.project_id = project_id;
    }
    /*public void SetTaskList(Vector<Task> taskList){
        this.taskList = taskList;
    }*/
    //End Setters
}
