package com.example.soccerman;

import org.json.JSONException;
import org.json.JSONObject;

import library.TeamFunctions;
import library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JoinTeamActivity extends Activity
{
	UserFunctions userFunctions;
	EditText inputTeamCode;
	Button btnJoinTeam, btnCancel;
	TextView joinTeamErrorMsg;
	
	// JSON response names
	private static String KEY_SUCCESS = "success";
	
	private static String JOIN_TEAM_ERROR = "Team with entered code doesn't exist";
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())) 
        {
        	setContentView(R.layout.join_team);
        	
        	inputTeamCode = (EditText) findViewById(R.id.joinTeamCode);
        	btnJoinTeam = (Button) findViewById(R.id.btnJoinTeam);
        	btnCancel = (Button) findViewById(R.id.btnCancelJoinTeam);
        	joinTeamErrorMsg = (TextView) findViewById(R.id.join_team_error);
        	
        	btnJoinTeam.setOnClickListener(new View.OnClickListener()
        	{
        		public void onClick(View view) 
        		{
        			String code = inputTeamCode.getText().toString().trim();
        			String player = userFunctions.getLoggedInUserId(getApplicationContext());
        			
        			if(code.equals(""))
        			{
        				joinTeamErrorMsg.setText(JOIN_TEAM_ERROR);
        				return;
        			}
        			
        			TeamFunctions teamFunctions = new TeamFunctions();
                	JSONObject json = teamFunctions.addPlayer(code, player);
                	
                	// check for join team response
                	try
                	{
                		if (json.getString(KEY_SUCCESS) != null) 
                		{
                			String res = json.getString(KEY_SUCCESS); 
                			if(Integer.parseInt(res) == 1)
                			{
                				joinTeamErrorMsg.setText("");
                				
                				Toast.makeText(JoinTeamActivity.this, "Joined team successfully", Toast.LENGTH_LONG).show();
                				
                				Intent dashboard = new Intent(JoinTeamActivity.this, DashboardActivity.class);
                        		startActivity(dashboard);
                        		finish();
                			}
                			else
                			{
                				joinTeamErrorMsg.setText(JOIN_TEAM_ERROR);
                			}
                		}
                	}
                	catch (JSONException e) 
                	{
            			e.printStackTrace();
            		}
        		}
        	});
        	
        	btnCancel.setOnClickListener(new View.OnClickListener()
			{
        		public void onClick(View view)
        		{
            		Intent dashboard = new Intent(JoinTeamActivity.this, DashboardActivity.class);
            		startActivity(dashboard);
            		finish();
            	}
			});
        	
        }
        else
        {
        	// user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            finish();
        }
	}
}
