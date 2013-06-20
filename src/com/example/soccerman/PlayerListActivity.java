package com.example.soccerman;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import library.Constants;
import library.TeamFunctions;
import library.UserFunctions;
import models.Player;
import models.TeamRole;
import adapters.PlayerAdapter;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

@SuppressLint("HandlerLeak")
public class PlayerListActivity extends ListActivity
{
	private ArrayList<Player> players = new ArrayList<Player>();
	private Runnable viewParts;
	private PlayerAdapter playerAdapter;
	private Button btnBack;
	private int teamId;
	private TeamRole teamRole;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_list);
		
		Bundle extras = getIntent().getExtras();
	    teamId = extras.getInt("teamId");
	    teamRole = extras.getParcelable("teamRole");
	    
		btnBack = (Button) findViewById(R.id.back_to_teams);
		playerAdapter = new PlayerAdapter(this, R.layout.player_list_item, players);
		setListAdapter(playerAdapter);

		btnBack.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				Intent teams = new Intent(PlayerListActivity.this, MyTeamListActivity.class);
				startActivity(teams);
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
	    inflater.inflate(R.menu.player_list_menu, menu);
	    
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    String currentUserId = new UserFunctions().getLoggedInUserId(getApplicationContext());
	    
	    if(teamRole.equals(TeamRole.PLAYER) || 
	    		players.get(info.position).getPlayerId() == Integer.parseInt(currentUserId))
	    {
	    	menu.getItem(0).setVisible(false);
	    }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.remove_player:
			int playerId = players.get(info.position).getPlayerId();
			TeamFunctions teamFunctions = new TeamFunctions();
			
			JSONObject json = teamFunctions.removePlayer(teamId, playerId);
			try
        	{
        		if (json.getString(Constants.KEY_SUCCESS) != null && Integer.parseInt(json.getString(Constants.KEY_SUCCESS)) == 1) 
        		{
    				Toast.makeText(PlayerListActivity.this, "Player removed successfully", Toast.LENGTH_LONG).show();
					finish();
					startActivity(getIntent());
    			}
    			else
    			{
    				Toast.makeText(PlayerListActivity.this, "Error occurred. Please try again later", Toast.LENGTH_LONG).show();
    			}
        	}
			catch (JSONException e) 
        	{
    			e.printStackTrace();
    		}
		}
		return false;
	}
	
	private Handler handler = new Handler()
   	{
    	public void handleMessage(Message msg)
   		{
			TeamFunctions teamFunctions = new TeamFunctions();
			players = teamFunctions.getTeamPlayers(teamId);
   			
   			playerAdapter = new PlayerAdapter(PlayerListActivity.this, R.layout.player_list_item, players);

   	        setListAdapter(playerAdapter);
   	        registerForContextMenu(getListView());
   		}
   	};
	
}
