public class Task {

	private String title;
	private String description;
	private Frequency frequency;
	private Urgency urgency;
	private Date deadline;
	private int user_id;
	
	public Task(){
		title = "Blank Task";
		description = "Empty";
		frequency = Frequency.ONCE;
		urgency = Urgency.LOW;
		deadline = new Date();
		user_id = 99;
	}
	
	public Task(String title, String description, Frequency frequency, 
				Urgency urgency, Date deadline, User user){
		this.title = title;
		this.description = description;
		this.frequency = frequency;
		this.urgency = urgency;
		this.deadline = deadline;
		this.user_id = user.id;
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
	public int getUserID(){
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
		this.user_id = user.id;
	}
	//End Setters
}