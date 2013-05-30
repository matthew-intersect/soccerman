package models;

public class Player
{
	private int playerId;
	private String name;
	
	public Player()
	{
	}

	public Player(int id, String name)
	{
		this.playerId = id;
		this.name = name;
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
}
