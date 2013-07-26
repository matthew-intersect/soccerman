package library;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;
 
public class UserFunctions 
{
    private JSONParser jsonParser;
    private Constants constants;
 
    public UserFunctions()
    {
        jsonParser = new JSONParser();
        constants = new Constants();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.LOGIN_TAG));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(constants.USERS_DB_URL, params);

        return json;
    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", Constants.REGISTER_TAG));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(constants.USERS_DB_URL, params);

        return json;
    }
    
    /**
     * function to change user password
     * @param id
     * @param password
     */
    public JSONObject changeUserPassword(String id, String password)
    {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", Constants.CHANGE_PASSWORD_TAG));
    	params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(constants.USERS_DB_URL, params);
    	
    	return json;
    }
     
    /**
     * Function get logged in user's id
     * */
    public String getLoggedInUserId(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	return db.getUserDetails().get("id");
    }
    
    /**
     * Function get logged in user's name
     * */
    public int getLoggedInUserName(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	return Integer.valueOf(db.getUserDetails().get("name"));
    }
    
    /**
     * Function get logged in user's email
     * */
    public String getLoggedInUserEmail(Context context)
    {
    	DatabaseHandler db = new DatabaseHandler(context);
    	return db.getUserDetails().get("email");
    }
    
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context)
    {
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0)
        {
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context)
    {
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
     
}