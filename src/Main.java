import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		final Scanner scan = new Scanner(System.in);
		String firstName, lastName;
		String taskName, taskDesc;

		// User Name
		System.out.print("Enter First Name: ");
		firstName = scan.nextLine();
		System.out.print("Enter Last Name: ");
		lastName = scan.nextLine();
		System.out.println("");
		
		User user = new User(new FullName(firstName, lastName));
		
		// Task
		System.out.print("Enter Task Name: ");
		taskName = scan.nextLine();
		System.out.println("Enter Task Description:");
		taskDesc = scan.nextLine();
		System.out.println("Choose Task Frequency:");
		System.out.println("1. Once");
		System.out.println("2. Daily");
		System.out.println("3. Weekly");
		System.out.println("4. Monthly");
		System.out.println("5. Annualy");
		
		System.out.println("Choose Task Urgency:");
		System.out.println("1. Lowest");
		System.out.println("2. Low");
		System.out.println("3. Medium");
		System.out.println("4. High");
		System.out.println("5. Highest");
		
		System.out.println("Enter Task Deadline:");
		System.out.print("Month (1-12): ");
		
		System.out.print("Day (1-31): ");
		
		System.out.print("Year: ");
		
		System.out.print("Hour (0-23): ");
		
		System.out.print("Minute (0-59): ");
		
		
		Task task1 = new Task();
		
		//user.AddTask(task1);
	}
}