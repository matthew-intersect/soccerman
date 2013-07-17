package com.example.soccerman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
 
import library.UserFunctions;
 
public class DashboardActivity extends Activity
{
	UserFunctions userFunctions;
	Button btnLogout, btnAddTeam, btnMyTeams, btnJoinTeam;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		userFunctions = new UserFunctions();
		if(userFunctions.isUserLoggedIn(getApplicationContext()))
		{
			setContentView(R.layout.dashboard);
			btnLogout = (Button) findViewById(R.id.btnLogout);
			btnAddTeam = (Button) findViewById(R.id.btnAddTeam);
			btnMyTeams = (Button) findViewById(R.id.btnMyTeams);
			btnJoinTeam = (Button) findViewById(R.id.btnJoinTeam);
			 
			btnLogout.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View arg0)
				{
					userFunctions.logoutUser(getApplicationContext());
					Intent login = new Intent(getApplicationContext(), LoginActivity.class);
					login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(login);
					finish();
				}
			});
			
			btnAddTeam.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View arg0)
				{
					Intent addTeam = new Intent(getApplicationContext(), AddTeamActivity.class);
					startActivity(addTeam);
					finish();
				}
			});
			
			btnMyTeams.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View arg0)
				{
					Intent teamList = new Intent(getApplicationContext(), MyTeamListActivity.class);
					startActivity(teamList);
				}
			});
			
			btnJoinTeam.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View arg0)
				{
					Intent joinTeam = new Intent(getApplicationContext(), JoinTeamActivity.class);
					startActivity(joinTeam);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.settings_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.change_password:
			{
				return true;
			}
			
		}
		return false;
	}
}