import java.util.Vector;


public class User {
	private int id;
	private String name;
	private Vector<Task> taskList = new Vector<Task>();
	private Vector<Project> projectList = new Vector<Project>();
	private Vector<User> friendList = new Vector<User>();
	
	//Getters
	public int GetId(){
		return id;
	}
	public String GetName(){
		return name;
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
	//End Getters
	
	//Setters
	public int SetId(int id){
		this.id = id;
	}
	public String SetName(String name){
		this.name = name;
	}
	public Vector<Task> SetTaskList(Vector<Task> taskList){
		this.taskList = taskList;
	}
	public Vector<Project> SetProjectList(Vector<Project> projectList){
		this.projectList = projectList;
	}
	public Vector<User> SetUserList(Vector<User> friendList){
		this.friendList = friendList;
	}
	//End Setters
	
	
}
