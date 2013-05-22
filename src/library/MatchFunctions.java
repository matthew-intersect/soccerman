package library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class MatchFunctions
{
	private JSONParser jsonParser;
    
    private static String dbURL = "http://10.0.2.2/ah_login_api/match_index.php";
     
    private static String ADD_MATCH_TAG = "add_match";
    
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
}
