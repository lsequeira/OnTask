package com.cse120.ontask.com.cse120.ontask.task;
import java.io.Serializable;

public class Task implements  Serializable{

    private static final long serialVersionUID = 6879876854546843515L;

    private int task_id;
    private String description;
    private String title;
    private Frequency frequency;
    private Urgency urgency;
    private Date deadline;
    private UserId user_id;
    private boolean forProject;
    private int taskProject_id;

    //For Debugging Purposes
    public Task(String title, String description, Date deadline, Urgency urgency) {
        this.title = title;
        this.description = description;
        frequency = Frequency.ONCE;
        this.urgency = urgency;
        this.deadline = deadline;
        user_id = new User().GetId();
    }

    public Task(int task_id, String title, String description, Frequency frequency, Date deadline, Urgency urgency, boolean forProject, int taskProject_id)
    {
        this.task_id = task_id;
        this.title = title;
        this.description = description;
        this.frequency = Frequency.NEVER;
        this.urgency = urgency;
        this.deadline = deadline;
        user_id = new User().GetId();
        this.forProject = forProject;
        this.taskProject_id = taskProject_id;
    }


        @Override
    public String toString() {
        return title;
    }

    public Task(String title, String description, Frequency frequency,
                Urgency urgency, Date deadline, User user){

        this.description = description;
        this.title = title;
        this.frequency = frequency;
        this.urgency = urgency;
        this.deadline = deadline;
        this.user_id = user.GetId();
    }

    public Task(){
        this("Blank Task", "Empty", Frequency.ONCE, Urgency.LOW, new Date(), new User());
    }

    /*
	public void EditTask(Task task){

	}
    */

    //Getters
    public int getTask_id() {
        return task_id;
    }
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
    public UserId getUserID(){
        return user_id;
    }
    public boolean getForProject(){
        return forProject;
    }
    public int getTaskProject_id(){
        return taskProject_id;
    }
    //End Getters

    //Setters
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
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
    public void setUser(User user){
        this.user_id = user.GetId();
    }
    public void setForProject(boolean forProject){
        this.forProject = forProject;
    }
    public void setTaskProject_id(int taskProject_id){
        this.taskProject_id = taskProject_id;
    }
    //End Setters
}