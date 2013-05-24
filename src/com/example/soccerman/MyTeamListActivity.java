package com.example.soccerman;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

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
	
	public void onListItemClick(ListView l, View v, int pos, long id)
	{
		l.showContextMenuForChild(v);
	}
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.team_list_menu, menu);
	    
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        int position = info.position;
        
	    //TODO: change menu contents based on role
	    TeamFunctions teamFunctions = new TeamFunctions();
	    UserFunctions userFunctions = new UserFunctions();
	    JSONObject json = teamFunctions.getTeamManager(teams.get(position).getId());
	    
	    try
		{
			int manager = json.getInt("manager");
			if(manager != Integer.parseInt(userFunctions.getLoggedInUserId(getApplicationContext())))
			{
				menu.getItem(0).setVisible(false); // hide add match menu option for players
			}
		} 
	    catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	    case R.id.add_match:
	        Intent addMatch = new Intent(MyTeamListActivity.this, AddMatchActivity.class);
	        addMatch.putExtra("teamId", teams.get(info.position).getId());
	        startActivity(addMatch);
	        return true;
	    case R.id.view_matches:
	    	Intent viewMatches = new Intent(MyTeamListActivity.this, MatchListActivity.class);
	    	viewMatches.putExtra("teamId", teams.get(info.position).getId());
	    	startActivity(viewMatches);
	    	return true;
	    }
	    return false;
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
   	        registerForContextMenu(getListView());
   		}
   	};
}
