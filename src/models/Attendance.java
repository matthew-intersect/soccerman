package models;

public enum Attendance
{
	YES("Yes"), NO("No"), NOT_RESPONDED("Not yet responded");
	
	private final String displayText;
	
	private Attendance(String display)
	{
		this.displayText = display;
	}
	
	public String getDisplayText()
	{
		return this.displayText;
	}
}
