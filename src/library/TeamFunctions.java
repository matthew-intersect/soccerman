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
     
    private static String add_team_tag = "add_team";
     
    // constructor
    public  TeamFunctions()
    {
        jsonParser = new JSONParser();
    }
    
    /**
     * function to store team
     * @param name
     * @param code
     * */
    public JSONObject addTeam(String name, String created_by)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", add_team_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("created_by", created_by));
        JSONObject json = jsonParser.getJSONFromUrl(dbURL, params);

        return json;
    }
    
}
