public class Date {

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	public Date() {
		year = 2000;
		month = 1;
		day = 1;
		hour = 0;
		minute = 1;
	}

	public Date( int year, int month, int day, int hour, int minute ) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}

	// Getters
	public int GetYear() {
		return year;
	}
	public int GetMonth() {
		return month;
	}
	public int GetDay() {
		return day;
	}
	public int GetHour() {
		return hour;
	}
	public int GetMinute() {
		return minute;
	}
	// End Getters

	// Setters
	public void SetYear( int year ) {
		this.year = year;
	}
	public void SetMonth( int month ) {
		this.month = month;
	}
	public void SetDay( int day ) {
		this.day = day;
	}
	public void SetHour( int hour ) {
		this.hour = hour;
	}
	public void SetMinute( int minute ) {
		this.minute = minute;
	}
	//End Setters
}

//This is the test comment
//this is testing branching
