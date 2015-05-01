package com.cse120.ontask.task_attributes;

import java.io.Serializable;

public class Task implements  Serializable{

    private static final long serialVersionUID = 6879876854546843515L;

    private int task_id;
    private int taskAutoIncKey;
    protected String description;
    protected String title;
    protected Urgency urgency;
    protected Date deadline;
    protected String user_id;
    private boolean forProject;
    private int taskProject_id;
    protected boolean isComplete;

    //For Debugging Purposes
    public Task(String title, String description, Date deadline, Urgency urgency) {
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.deadline = deadline;
    }

    public Task(String title, String description, Date deadline, Urgency urgency, boolean forProject, int taskProject_id, boolean isComplete)
    {
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.deadline = deadline;
        this.forProject = forProject;
        this.taskProject_id = taskProject_id;
        this.isComplete = isComplete;
    }

    public Task(int taskAutoIncKey, String title, String description, Date deadline, Urgency urgency, boolean forProject, int taskProject_id, boolean isComplete, int task_id, String user_id)
    {
        this.taskAutoIncKey = taskAutoIncKey;
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.deadline = deadline;
        this.forProject = forProject;
        this.taskProject_id = taskProject_id;
        this.isComplete = isComplete;
        this.task_id = task_id;
        this.user_id = user_id;
    }


        @Override
    public String toString() {
        return title;
    }

    public Task(String title, String description, Urgency urgency, Date deadline, User user){

        this.description = description;
        this.title = title;
        this.urgency = urgency;
        this.deadline = deadline;
    }

    public Task(){
        this("Blank Task", "Empty", Urgency.LOW, new Date(), new User());
    }

    /*
	public void EditTask(Task task){

	}
    */

    //Getters
    public int getTask_id() {
        return task_id;
    }
    public int getTaskAutoIncKey() {
        return taskAutoIncKey;
    }
    public String getTitle(){
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
    public String getUserId(){
        return user_id;
    }
    public boolean getForProject(){
        return forProject;
    }
    public int getTaskProject_id(){
        return taskProject_id;
    }
    public boolean getIsCompleted(){
        return isComplete;
    }
    //End Getters

    //Setters
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
    public void setTaskAutoIncKey(int taskAutoIncKey) {
        this.taskAutoIncKey = taskAutoIncKey;
    }
    public void setTitle(String title){
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
    public void setUserId(String id){
        this.user_id = id;
    }
    public void setForProject(boolean forProject){
        this.forProject = forProject;
    }
    public void setTaskProject_id(int taskProject_id){
        this.taskProject_id = taskProject_id;
    }
    public void setIsCompleted(boolean isComplete){
        this.isComplete = isComplete;
    }
    //End Setters
}