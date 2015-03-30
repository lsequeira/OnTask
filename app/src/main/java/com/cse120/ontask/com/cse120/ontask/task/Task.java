package com.cse120.ontask.com.cse120.ontask.task;


public class Task {

    private String title;
    private String description;
    private Frequency frequency;
    private Urgency urgency;
    private Date deadline;
    private UserId user_id;

    public Task(String title, String description, Frequency frequency,
                Urgency urgency, Date deadline, User user){
        this.title = title;
        this.description = description;
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
    public void SetUser(User user){
        this.user_id = user.GetId();
    }
    //End Setters
}