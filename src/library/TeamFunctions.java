package library;

import java.util.ArrayList;
import java.util.List;

import models.Team;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamFunctions
{

	private JSONParser jsonParser;
    
    private static String dbURL = "http://10.0.2.2/ah_login_api/";
     
    private static String ADD_TEAM_TAG = "add_team";
    private static String JOIN_TEAM_TAG = "join_team";
    private static String PLAYERS_TEAM_TAG = "players_teams";
    private static String GET_MANAGER_TAG = "get_team_manager";
     
    // constructor
    public TeamFunctions()
    {
        jsonParser = new JSONParser();
    }
    
    /**
     * function to store team
     * @param name
     * @param user created by
     **/
    public JSONObject addTeam(String name, String created_by, int playerManager)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", ADD_TEAM_TAG));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("created_by", created_by));
        params.add(new BasicNameValuePair("player_manager", String.valueOf(playerManager)));
        JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);

        return json;
    }

    /**
     * function to add player to team
     * @param team code
     * @param user to join
     **/
	public JSONObject addPlayer(String code, String player)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", JOIN_TEAM_TAG));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("player", player));
        JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);

        return json;
	}

	/**
     * function to get all teams of a player
     * @param player
	 * @throws JSONException 
     **/
	public ArrayList<Team> getAllTeams(String player)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", PLAYERS_TEAM_TAG));
        params.add(new BasicNameValuePair("player", player));
        try
        {
        	JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);
        	if(Integer.parseInt(json.getString("success")) == 1)
        	{
	        	ArrayList<Team> teams = new ArrayList<Team>();
	        	JSONArray array = json.getJSONArray("teams");
	        	
	        	for(int i=0;i<array.length();i++)
	        	{
	        		JSONObject team = array.getJSONObject(i);
	        		teams.add(new Team(team.getString("name"), team.getInt("id"), team.getString("manager")));
	        	}
	        	return teams;
        	}
        	else {
        		return new ArrayList<Team>();
        	}
        }
        catch (JSONException e) 
        {
        	return new ArrayList<Team>();
        }
	}
    
	/**
     * function to get manager information for a team
     * @param team id
     **/
	public JSONObject getTeamManager(int team)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", GET_MANAGER_TAG));
        params.add(new BasicNameValuePair("team", String.valueOf(team)));
        JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);

        return json;
	}
}
