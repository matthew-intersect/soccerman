package com.example.soccerman;

import library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTeamActivity extends Activity
{
	UserFunctions userFunctions;
	Button btnAddTeam, btnCancel;
	EditText inputTeamName;
	
	@Override
		public void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			
			userFunctions = new UserFunctions();
	        if(userFunctions.isUserLoggedIn(getApplicationContext()))
	        {
	        	setContentView(R.layout.add_team);
	        	
	        	inputTeamName = (EditText) findViewById(R.id.teamName);
	        	btnAddTeam = (Button) findViewById(R.id.btnAddTeam);
	            btnCancel = (Button) findViewById(R.id.btnCancelAddTeam);
	            
	            
	            
	            btnCancel.setOnClickListener(new View.OnClickListener()
	            {
	            	public void onClick(View view) {
	            		Intent dashboard = new Intent(AddTeamActivity.this, DashboardActivity.class);
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
