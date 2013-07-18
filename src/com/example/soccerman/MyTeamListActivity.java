package com.example.soccerman;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import library.Constants;
import library.TeamFunctions;
import library.UserFunctions;
import models.Team;
import models.TeamRole;

import adapters.TeamAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MyTeamListActivity extends ListActivity
{
	private ArrayList<Team> teams = new ArrayList<Team>();
	private Runnable viewParts;
	private TeamAdapter teamAdapter;
	Button btnBack;
	private TeamRole teamRole;
	
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
        
	    TeamFunctions teamFunctions = new TeamFunctions();
	    UserFunctions userFunctions = new UserFunctions();
	    JSONObject json = teamFunctions.getTeamManager(teams.get(position).getId());
	    
	    try
		{
	    	int manager = json.getInt("manager");
			if(manager == Integer.parseInt(userFunctions.getLoggedInUserId(getApplicationContext())))
			{
				if(json.getInt("player_manager") == 1)
				{
					teamRole = TeamRole.PLAYER_MANAGER;
				}
				else
				{
					teamRole = TeamRole.MANAGER;
				}
			}
			else
			{
				teamRole = TeamRole.PLAYER;
				menu.getItem(0).setVisible(false); // hide add match menu option for players
				menu.getItem(3).setVisible(false); // hide view team code option for players
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
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId())
	    {
		    case R.id.add_match:
		        Intent addMatch = new Intent(MyTeamListActivity.this, AddMatchActivity.class);
		        addMatch.putExtra("teamId", teams.get(info.position).getId());
		        addMatch.putExtra("teamHomeGround", teams.get(info.position).getHomeGround());
		        startActivity(addMatch);
		        return true;
		    case R.id.view_matches:
		    	Intent viewMatches = new Intent(MyTeamListActivity.this, MatchListActivity.class);
		    	viewMatches.putExtra("teamId", teams.get(info.position).getId());
		    	viewMatches.putExtra("teamHomeGround", teams.get(info.position).getHomeGround());
		    	viewMatches.putExtra("teamRole", (Parcelable) teamRole);
		    	startActivity(viewMatches);
		    	return true;
		    case R.id.view_players:
		    	Intent viewPlayers = new Intent(MyTeamListActivity.this, PlayerListActivity.class);
		    	viewPlayers.putExtra("teamId", teams.get(info.position).getId());
		    	viewPlayers.putExtra("teamHomeGround", teams.get(info.position).getHomeGround());
		    	viewPlayers.putExtra("teamRole", (Parcelable) teamRole);
		    	startActivity(viewPlayers);
		    	return true;
		    case R.id.view_team_code:
		    	final Dialog viewTeamCode = new Dialog(MyTeamListActivity.this);
		    	viewTeamCode.setTitle(teams.get(info.position).getName() + " Team Code");
				viewTeamCode.setContentView(R.layout.view_team_code);
				
				final TextView teamCode = (TextView) viewTeamCode.findViewById(R.id.view_team_code);
				TextView teamCodeMessage = (TextView) viewTeamCode.findViewById(R.id.view_team_code_message);
				Button btnOk = (Button) viewTeamCode.findViewById(R.id.view_team_code_ok);
				Button btnChangeCode = (Button) viewTeamCode.findViewById(R.id.btnChangeCode);
				teamCode.setText(teams.get(info.position).getCode());
				teamCodeMessage.setText(Constants.TEAM_CODE_MESSAGE);
				
				btnOk.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						viewTeamCode.dismiss();
					}
				});
				
				btnChangeCode.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						TeamFunctions teamFunctions = new TeamFunctions();
						JSONObject json = teamFunctions.changeTeamCode(teams.get(info.position).getId());
						try
						{
							if(Integer.parseInt(json.getString(Constants.KEY_SUCCESS)) == 1)
							{
								teamCode.setText(json.getString("code"));
								Toast.makeText(MyTeamListActivity.this, "Code changed successfully", Toast.LENGTH_LONG).show();
							}
							else
							{
								Toast.makeText(MyTeamListActivity.this, "Error occurred. Please try again later", Toast.LENGTH_LONG).show();
								viewTeamCode.dismiss();
							}
						}
						catch (JSONException e) 
	                	{
	            			e.printStackTrace();
	            		}
					}
				});
				
		    	viewTeamCode.show();
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
