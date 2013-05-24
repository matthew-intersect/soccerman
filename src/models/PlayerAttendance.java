package models;

public class PlayerAttendance
{
	private int playerId;
	private String name;
	private Attendance attendance;
	
	public PlayerAttendance()
	{
	}
	
	public PlayerAttendance(int id, String name, Attendance attendance)
	{
		this.playerId = id;
		this.name = name;
		this.attendance = attendance;
	}
	
	public int getPlayerId()
	{
		return playerId;
	}
	
	public void setPlayerId(int playerId)
	{
		this.playerId = playerId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Attendance getAttendance()
	{
		return attendance;
	}

	public void setAttendance(Attendance attendance)
	{
		this.attendance = attendance;
	}
}
