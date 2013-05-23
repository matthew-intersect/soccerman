package library;

import java.util.ArrayList;
import java.util.List;

import models.Match;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MatchFunctions
{
	private JSONParser jsonParser;
    
    private static String dbURL = "http://10.0.2.2/ah_login_api/match_index.php";
     
    private static String ADD_MATCH_TAG = "add_match";
    private static String TEAM_MATCHES = "get_matches";
    private static String ADD_ATTENDANCE = "add_attendance";
    
    public MatchFunctions()
    {
    	jsonParser = new JSONParser();
    }
    
    /**
     * function to store match
     * @param team id
     * @param opponent
     * @param venue
     * @param game date and time
     **/
    public JSONObject addMatch(int team, String opponent, String venue, long time)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", ADD_MATCH_TAG));
        params.add(new BasicNameValuePair("team", String.valueOf(team)));
        params.add(new BasicNameValuePair("opponent", opponent));
        params.add(new BasicNameValuePair("venue", venue));
        params.add(new BasicNameValuePair("time", String.valueOf(time)));
        JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);
        
        return json;
    }
    
    /**
     * function to get all matches for a team
     * @param team id
     **/
	public ArrayList<Match> getAllMatches(String team)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", TEAM_MATCHES));
        params.add(new BasicNameValuePair("team", team));
        try
        {
        	JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);
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
        	else {
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
        params.add(new BasicNameValuePair("tag", ADD_ATTENDANCE));
        params.add(new BasicNameValuePair("player", String.valueOf(player)));
        params.add(new BasicNameValuePair("match", String.valueOf(match)));
        params.add(new BasicNameValuePair("attend", String.valueOf(attendance)));
        
        JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);
        
        return json;
	}
}
