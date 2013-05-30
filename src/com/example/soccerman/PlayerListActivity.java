package com.example.soccerman;

import java.util.ArrayList;

import library.TeamFunctions;
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
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;

@SuppressLint("HandlerLeak")
public class PlayerListActivity extends ListActivity
{
	private ArrayList<Player> players = new ArrayList<Player>();
	private Runnable viewParts;
	private PlayerAdapter playerAdapter;
	private Button btnBack;
	private int teamId;
	private TeamRole teamRole;
	
	private static String KEY_SUCCESS = "success";
	
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
	    
	    if(teamRole.equals(TeamRole.MANAGER))
	    {
	    	menu.getItem(0).setVisible(false);
	    }
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
