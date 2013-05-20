package models;

public enum MatchState
{
	HOME("Home"), AWAY("Away");
	
	private final String displayText;
	
	private MatchState(String display)
	{
		this.displayText = display;
	}
	
	public String getDisplayText()
	{
		return this.displayText;
	}
}

