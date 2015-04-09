package com.cse120.ontask.com.cse120.ontask.task;

import java.util.Vector;

public class Project {
    private String title;
    private String description;
    private Frequency frequency;
    private Urgency urgency;
    private Date deadline;
    private Vector<User> memberList;
    private Vector<User> adminList;
    private Vector<Task> taskList;

    public Project(String title, String description, Frequency frequency,
                   Urgency urgency, Date deadline){
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.urgency = urgency;
        this.deadline = deadline;
        memberList = new Vector<User>();
        adminList = new Vector<User>();
        taskList = new Vector<Task>();
    }

    public Project(){
        this("Blank Project", "Empty", Frequency.ONCE, Urgency.LOW, new Date());
    }

    public void EditProject(Project project){

    }

    public void SortList(){

    }

    //Getters
    public String GetTitle(){
        return title;
    }
    public String GetDescription(){
        return description;
    }
    public Frequency GetFrequency(){
        return frequency;
    }
    public Urgency GetUrgency(){
        return urgency;
    }
    public Date GetDeadline(){
        return deadline;
    }
    public Vector<User> GetMemberList(){
        return (Vector<User>) memberList;
    }
    public Vector<User> GetAdminList(){
        return (Vector<User>) adminList;
    }
    public Vector<Task> GetTaskList(){
        return (Vector<Task>) taskList;
    }
    //End Getters

    //Setters
    public void SetTitle(String title){
        this.title = title;
    }
    public void SetDescription(String description){
        this.description = description;
    }
    public void SetFrequency(Frequency frequency){
        this.frequency = frequency;
    }
    public void SetUrgency(Urgency urgency){
        this.urgency = urgency;
    }
    public void SetDeadline(Date deadline){
        this.deadline = deadline;
    }
    public void SetMemberList(Vector<User> memberList){
        this.memberList = memberList;
    }
    public void SetAdminList(Vector<User> adminList){
        this.adminList = adminList;
    }
    public void SetTaskList(Vector<Task> taskList){
        this.taskList = taskList;
    }
    //End Setters
}
