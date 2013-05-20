package models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Match
{
	private int id;
	private int teamId;
	private String opponent;
	private MatchState matchState;
	private int round;
	private String venue;
	private Calendar time;
	
	public Match()
	{
	}
	
	public Match(int id, int teamId, String opponent, MatchState state, int round, String venue, long time)
	{
		this.id =id;
		this.teamId = teamId;
		this.opponent = opponent;
		this.matchState = state;
		this.round = round;
		this.venue = venue;
		this.time = new GregorianCalendar();
		this.time.setTimeInMillis(time);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getTeamId()
	{
		return teamId;
	}

	public void setTeamId(int teamId)
	{
		this.teamId = teamId;
	}

	public String getOpponent()
	{
		return opponent;
	}

	public void setOpponent(String opponent)
	{
		this.opponent = opponent;
	}

	public MatchState getMatchState()
	{
		return matchState;
	}

	public void setMatchState(MatchState matchState)
	{
		this.matchState = matchState;
	}

	public int getRound()
	{
		return round;
	}

	public void setRound(int round)
	{
		this.round = round;
	}

	public String getVenue()
	{
		return venue;
	}

	public void setVenue(String venue)
	{
		this.venue = venue;
	}

	public Calendar getTime()
	{
		return time;
	}

	public void setTime(Calendar time)
	{
		this.time = time;
	}
}
