package com.example.soccerman;

import java.util.ArrayList;

import library.TeamFunctions;
import library.UserFunctions;
import models.Team;

import adapters.TeamAdapter;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

@SuppressLint("HandlerLeak")
public class MyTeamListActivity extends ListActivity
{
	private ArrayList<Team> teams = new ArrayList<Team>();
	private Runnable viewParts;
	private TeamAdapter teamAdapter;
	Button btnBack;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_team_list);
		
		btnBack = (Button) findViewById(R.id.back_to_menu);
		teamAdapter = new TeamAdapter(this, R.layout.team_list_item, teams);
		setListAdapter(teamAdapter);
		
		btnBack.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				Intent home = new Intent(MyTeamListActivity.this, DashboardActivity.class);
				startActivity(home);
				finish();
			}
		});

		viewParts = new Runnable()
		{
        	public void run()
        	{
        		handler.sendEmptyMessage(0);
        	}
        };
        
        Thread thread =  new Thread(null, viewParts, "MagentoBackground");
        thread.start();
	}
        
	private Handler handler = new Handler()
   	{
    	public void handleMessage(Message msg)
   		{
			TeamFunctions teamFunctions = new TeamFunctions();
   			String currentUser = new UserFunctions().getLoggedInUserId(getApplicationContext());
			teams = teamFunctions.getAllTeams(currentUser);
   			
   			teamAdapter = new TeamAdapter(MyTeamListActivity.this, R.layout.team_list_item, teams);

   	        setListAdapter(teamAdapter);
   		}
   	};
}
