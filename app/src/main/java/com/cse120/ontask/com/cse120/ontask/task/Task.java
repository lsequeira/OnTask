package com.cse120.ontask.com.cse120.ontask.task;


public class Task {


    private String description;
    private String title;
    private Frequency frequency;
    private Urgency urgency;
    private Date deadline;
    private UserId user_id;

    //For Debugging Purposes
    public Task(String title, String description, Date deadline)
    {
        this.title = title;
        this.description = description;
        frequency = Frequency.ONCE;
        urgency = Urgency.LOW;
        this.deadline = deadline;
        user_id = new User().GetId();

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
    //End Setters
}