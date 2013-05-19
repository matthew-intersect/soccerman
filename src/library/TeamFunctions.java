package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class TeamFunctions
{

	private JSONParser jsonParser;
    
    private static String dbURL = "http://10.0.2.2/ah_login_api/";
     
    private static String ADD_TEAM_TAG = "add_team";
    private static String JOIN_TEAM_TAG = "join_team";
     
    // constructor
    public  TeamFunctions()
    {
        jsonParser = new JSONParser();
    }
    
    /**
     * function to store team
     * @param name
     * @param user created by
     * */
    public JSONObject addTeam(String name, String created_by)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", ADD_TEAM_TAG));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("created_by", created_by));
        JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);

        return json;
    }

    /**
     * function to add player to team
     * @param team code
     * @param user to join
     * */
	public JSONObject addPlayer(String code, String player)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", JOIN_TEAM_TAG));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("player", player));
        JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);

        return json;
	}
    
}
