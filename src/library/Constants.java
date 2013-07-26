package library;

import java.io.IOException;
import java.util.Properties;

public final class Constants
{
	// database request codes and tags
	public String DATABASE_NAME = "android_api";
	public static final int DATABASE_VERSION = 2;
	public String USERS_DB_URL = "http://10.0.2.2/ah_login_api/user_index.php";
	public String TEAMS_DB_URL = "http://10.0.2.2/ah_login_api/team_index.php";
	public String MATCHESS_DB_URL = "http://10.0.2.2/ah_login_api/match_index.php";
	
	public static final String LOGIN_TAG = "login";
	public static final String REGISTER_TAG = "register";
	public static final String CHANGE_PASSWORD_TAG = "change_password";
	public static final String ADD_MATCH_TAG = "add_match";
	public static final String TEAM_MATCHES = "get_matches";
	public static final String ADD_ATTENDANCE = "add_attendance";
	public static final String GET_ATTENDANCE = "get_attendance";
	public static final String GET_PLAYER_ATTENDANCE = "get_player_attendance";
	public static final String ADD_TEAM_TAG = "add_team";
	public static final String JOIN_TEAM_TAG = "join_team";
	public static final String PLAYERS_TEAM_TAG = "players_teams";
	public static final String GET_MANAGER_TAG = "get_team_manager";
	public static final String GET_PLAYERS_TAG = "get_team_players";
	public static final String CHANGE_CODE_TAG = "change_team_code";
	public static final String REMOVE_PLAYER_TAG = "remove_player";
	
	
	// JSON response codes
	public static final String KEY_SUCCESS = "success";
	public static final String KEY_ERROR = "error";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ID = "id";
	public static final String KEY_CREATED_AT = "created_at";
	public static final String KEY_CODE = "code";
	
	
	// messages
	public static final String TEAM_CODE_MESSAGE = "For players to join this team, get them to use the following code: ";
	public static final String TEAM_ADD_MESSAGE = "Your team has been successfully created. For players to join this team, get them to use the following code: ";
	public static final String JOIN_TEAM_ERROR = "Team with entered code doesn't exist";
	public static final String PASSWORD_LENGTH_ERROR = "Password must be between 6 and 20 characters";
	public static final String OLD_PASSWORD_ERROR = "Old password is incorrect";
	public static final String NEW_PASSWORD_ERROR = "New passwords don't match";
	public static final String BASIC_ERROR = "Error occurred. Please try again later";
	public static final String ATTENDANCE_SUCCESS = "Attendance recorded successfully";
	
	public Constants()
	{
		Properties prop = new Properties();
		
		try
		{
			prop.load(getClass().getResourceAsStream("dev.properties"));
			DATABASE_NAME = prop.getProperty("DATABASE_NAME");
			USERS_DB_URL = prop.getProperty("USERS_DB_URL");
			TEAMS_DB_URL = prop.getProperty("TEAMS_DB_URL");
			MATCHESS_DB_URL = prop.getProperty("MATCHES_DB_URL");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
