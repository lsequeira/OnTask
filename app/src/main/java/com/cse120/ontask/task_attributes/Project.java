package com.cse120.ontask.task_attributes;

public class Project extends Task {
    private String project_id;
    private int projectAutoIncKey;
   /* private String title;
    private String description;
    private Urgency urgency;
    private Date deadline;
    private Vector<User> memberList;
    private Vector<User> adminList;
    //private List<Task> taskList;
    private boolean isComplete;
*/
    public Project(String title, String project_id, String description, Date deadline,
                   Urgency urgency, boolean isCompleted){
        //this.projectAutoIncKey = project_key;
        this.project_id = project_id;
        this.title = title;
        this.description = description;
        //this.frequency = frequency;
        this.urgency = urgency;
        this.deadline = deadline;
        //memberList = new Vector<User>();
        //adminList = new Vector<User>();
        //this.taskList = taskList;
        this.isComplete = isCompleted;
    }

    public Project(int project_key, String title, String project_id, String description, Date deadline,
                   Urgency urgency, boolean isCompleted){
        this.projectAutoIncKey = project_key;
        this.project_id = project_id;
        this.title = title;
        this.description = description;
        //this.frequency = frequency;
        this.urgency = urgency;
        this.deadline = deadline;
        //memberList = new Vector<User>();
        //adminList = new Vector<User>();
        //this.taskList = taskList;
        this.isComplete = isCompleted;
    }

    public Project(){

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
    public String getProject_id(){
        return project_id;
    }
    /*public boolean getIsCompleted(){
        return isCompleted;
    }
    public Vector<Task> getTaskList(){
        return (Vector<Task>) taskList;
    }*/
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
    public void setProject_id(String project_id){
        this.project_id = project_id;
    }
    /*
    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }
    public void SetTaskList(Vector<Task> taskList){
        this.taskList = taskList;
    }
    */
    //End Setters
}
