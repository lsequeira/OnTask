public class UserId {
	
	private int user_id;
	
	private int GenerateId(){
		// Generate Id
		// Random? Incrementing?
		return 69;
	}
	
	public UserId(){
		user_id = GenerateId();
	}
	
	public int GetId(){
		return user_id;
	}
}
