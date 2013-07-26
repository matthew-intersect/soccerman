package library;

import java.util.ArrayList;
import java.util.List;

import models.Attendance;
import models.Match;
import models.PlayerAttendance;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MatchFunctions
{
	private JSONParser jsonParser;
	private Constants constants;

    public MatchFunctions()
    {
    	jsonParser = new JSONParser();
    	constants = new Constants();
    }
    
    /**
     * function to store match
     * @param team id
     * @param opponent
     * @param venue
     * @param game date and time
     **/
    public JSONObject addMatch(int team, String opponent, String venue, String home, long time)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.ADD_MATCH_TAG));
        params.add(new BasicNameValuePair("team", String.valueOf(team)));
        params.add(new BasicNameValuePair("opponent", opponent));
        params.add(new BasicNameValuePair("venue", venue));
        params.add(new BasicNameValuePair("home", home));
        params.add(new BasicNameValuePair("time", String.valueOf(time)));
        JSONObject json = jsonParser.getJSONFromUrl(constants.MATCHESS_DB_URL, params);
        
        return json;
    }
    
    /**
     * function to get all matches for a team
     * @param team id
     **/
	public ArrayList<Match> getAllMatches(String team)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.TEAM_MATCHES));
        params.add(new BasicNameValuePair("team", team));
        try
        {
        	JSONObject json = jsonParser.getJSONFromUrl(constants.MATCHESS_DB_URL, params);
        	if(Integer.parseInt(json.getString("success")) == 1)
        	{
	        	ArrayList<Match> matches = new ArrayList<Match>();
	        	JSONArray array = json.getJSONArray("matches");
	        	
	        	for(int i=0;i<array.length();i++)
	        	{
	        		JSONObject match = array.getJSONObject(i);
	        		matches.add(new Match(match.getInt("id"), match.getInt("team"), match.getString("opponent"), match.getString("venue"), match.getLong("time")));
	        	}
	        	return matches;
        	}
        	else
        	{
        		return new ArrayList<Match>();
        	}
        }
        catch (JSONException e) 
        {
        	return new ArrayList<Match>();
        }
	}
	
	/**
     * function to add match attendance for a player
     * @param player id
     * @param match id
     * @param attendance response
     **/
	public JSONObject addAttendance(String player, int match, int attendance)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.ADD_ATTENDANCE));
        params.add(new BasicNameValuePair("player", String.valueOf(player)));
        params.add(new BasicNameValuePair("match", String.valueOf(match)));
        params.add(new BasicNameValuePair("attend", String.valueOf(attendance)));
        
        JSONObject json = jsonParser.getJSONFromUrl(constants.MATCHESS_DB_URL, params);
        
        return json;
	}

	/**
     * function to get all player attendances for a match
     * @param match id
     **/
	public ArrayList<PlayerAttendance> getMatchAttendance(String match)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.GET_ATTENDANCE));
        params.add(new BasicNameValuePair("match", match));
        try
        {
        	JSONObject json = jsonParser.getJSONFromUrl(constants.MATCHESS_DB_URL, params);
        	if(Integer.parseInt(json.getString("success")) == 1)
        	{
	        	ArrayList<PlayerAttendance> playerAttendances = new ArrayList<PlayerAttendance>();
	        	JSONArray array = json.getJSONArray("attendances");
	        	
	        	Attendance attendance = null;
	        	for(int i=0;i<array.length();i++)
	        	{
	        		JSONObject playerAttendance = array.getJSONObject(i);
	        		int attendValue = playerAttendance.getInt("attendance");
	        		switch(attendValue)
	        		{
		        		case 1: 
		        			attendance = Attendance.YES;
		        			break;
		        		case 0: 
		        			attendance = Attendance.NO;
		        			break;
		        		case -1: 
		        			attendance = Attendance.NOT_RESPONDED;
		        			break;
	        		}
	        		playerAttendances.add(new PlayerAttendance(playerAttendance.getInt("player_id"), playerAttendance.getString("player_name"), attendance));
	        	}
	        	return playerAttendances;
        	}
        	else {
        		return new ArrayList<PlayerAttendance>();
        	}
        }
        catch (JSONException e) 
        {
        	return new ArrayList<PlayerAttendance>();
        }
	}
	
	/**
     * function to get all player attendances for a match
     * @param match id
     **/
	public Attendance getPlayerMatchAttendance(int match, String player)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.GET_PLAYER_ATTENDANCE));
        params.add(new BasicNameValuePair("match", String.valueOf(match)));
        params.add(new BasicNameValuePair("player", player));
        try
        {
        	JSONObject json = jsonParser.getJSONFromUrl(constants.MATCHESS_DB_URL, params);
        	String attendance = json.getString("attendance");
        	if(attendance.equals("1"))
        	{
        		return Attendance.YES;
        	}
        	else if(attendance.equals("0"))
        	{
        		return Attendance.NO;
        	}
        }
        catch (JSONException e) 
        {
        	return Attendance.NOT_RESPONDED;
        }
        return Attendance.NOT_RESPONDED;
	}
}
