public class FullName {
	
	private String firstName;
	private String lastName;
	
	public FullName(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public FullName(){
		this("John", "Smith");
	}
	
	// Getters
	public String GetFirstName(){
		return firstName;
	}
	public String GetLastName(){
		return lastName;
	}
	// End Getters
	
	// Setters
	public void SetFirstName(String firstName){
		this.firstName = firstName;
	}
	public void SetLastName(String lastName){
		this.lastName = lastName;
	}
	// End Setters
}
