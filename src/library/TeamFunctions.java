package library;

import java.util.ArrayList;
import java.util.List;

import models.Player;
import models.Team;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamFunctions
{
	private JSONParser jsonParser;

	public TeamFunctions()
	{
		jsonParser = new JSONParser();
	}
    
	/**
	 * function to store team
	 * @param name
	 * @param user created by
	 **/
    public JSONObject addTeam(String name, String created_by, int playerManager, String homeGround)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.ADD_TEAM_TAG));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("created_by", created_by));
        params.add(new BasicNameValuePair("player_manager", String.valueOf(playerManager)));
        params.add(new BasicNameValuePair("home_ground", homeGround));
        JSONObject json = jsonParser.getJSONFromUrl(Constants.TEAMS_DB_URL, params);

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
        params.add(new BasicNameValuePair("tag", Constants.JOIN_TEAM_TAG));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("player", player));
        JSONObject json = jsonParser.getJSONFromUrl(Constants.TEAMS_DB_URL, params);

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
        params.add(new BasicNameValuePair("tag", Constants.PLAYERS_TEAM_TAG));
        params.add(new BasicNameValuePair("player", player));
        try
        {
        	JSONObject json = jsonParser.getJSONFromUrl(Constants.TEAMS_DB_URL, params);
        	if(Integer.parseInt(json.getString("success")) == 1)
        	{
	        	ArrayList<Team> teams = new ArrayList<Team>();
	        	JSONArray array = json.getJSONArray("teams");
	        	
	        	for(int i=0;i<array.length();i++)
	        	{
	        		JSONObject team = array.getJSONObject(i);
	        		teams.add(new Team(team.getString("name"), team.getInt("id"), team.getString("code"),
	        				team.getString("manager"), team.getString("home_ground")));
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
     * function to get all players of a team
     * @param team id
     **/
	public ArrayList<Player> getTeamPlayers(int team)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.GET_PLAYERS_TAG));
        params.add(new BasicNameValuePair("team", String.valueOf(team)));

        try
        {
        	JSONObject json = jsonParser.getJSONFromUrl(Constants.TEAMS_DB_URL, params);
        	if(Integer.parseInt(json.getString("success")) == 1)
        	{
	        	ArrayList<Player> players = new ArrayList<Player>();
	        	JSONArray array = json.getJSONArray("players");
	        	
	        	for(int i=0;i<array.length();i++)
	        	{
	        		JSONObject player = array.getJSONObject(i);
	        		players.add(new Player(player.getInt("id"), player.getString("name")));
	        	}
	        	return players;
        	}
        	else {
        		return new ArrayList<Player>();
        	}
        }
        catch (JSONException e) 
        {
        	return new ArrayList<Player>();
        }
	}
    
	/**
     * function to get manager information for a team
     * @param team id
     **/
	public JSONObject getTeamManager(int team)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.GET_MANAGER_TAG));
        params.add(new BasicNameValuePair("team", String.valueOf(team)));
        JSONObject json = jsonParser.getJSONFromUrl(Constants.TEAMS_DB_URL, params);

        return json;
	}
	
	/**
     * function to change a team's code
     * @param team id
     **/
	public JSONObject changeTeamCode(int team)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.CHANGE_CODE_TAG));
        params.add(new BasicNameValuePair("team", String.valueOf(team)));
        JSONObject json = jsonParser.getJSONFromUrl(Constants.TEAMS_DB_URL, params);
        
        return json;
	}
	
	/**
     * function to remove a player from a team
     * @param team id
     * @param player id
     **/
	public JSONObject removePlayer(int team, int player)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.REMOVE_PLAYER_TAG));
        params.add(new BasicNameValuePair("team", String.valueOf(team)));
        params.add(new BasicNameValuePair("player", String.valueOf(player)));
        JSONObject json = jsonParser.getJSONFromUrl(Constants.TEAMS_DB_URL, params);
        
        return json;
	}
}
