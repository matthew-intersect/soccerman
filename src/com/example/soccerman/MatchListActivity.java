package com.example.soccerman;

import java.util.ArrayList;

import library.MatchFunctions;
import library.TeamFunctions;
import library.UserFunctions;
import models.Match;
import adapters.MatchAdapter;
import adapters.TeamAdapter;
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
import android.widget.AdapterView.AdapterContextMenuInfo;

@SuppressLint("HandlerLeak")
public class MatchListActivity extends ListActivity
{
	private ArrayList<Match> matches = new ArrayList<Match>();
	private Runnable viewParts;
	private MatchAdapter matchAdapter;
	Button btnBack;
	private int teamId;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_match_list);
		
		Bundle extras = getIntent().getExtras();
	    teamId = extras.getInt("teamId");
		
		btnBack = (Button) findViewById(R.id.back_to_teams);
		matchAdapter = new MatchAdapter(this, R.layout.match_list_item, matches);
		setListAdapter(matchAdapter);
		
		btnBack.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				Intent teams = new Intent(MatchListActivity.this, MyTeamListActivity.class);
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
	    inflater.inflate(R.menu.match_list_menu, menu);
	    //menu.getItem(0).setEnabled(false);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	    case R.id.change_attendance:
	    	//TODO: add in change attendance intent
	        return true;
	    case R.id.view_attendance:
	    	//TODO: view attendance intent
	    	return true;
	    }
	    return false;
	}
	
	private Handler handler = new Handler()
   	{
    	public void handleMessage(Message msg)
   		{
			MatchFunctions matchFunctions = new MatchFunctions();
			matches = matchFunctions.getAllMatches(String.valueOf(teamId));
   			
   			matchAdapter = new MatchAdapter(MatchListActivity.this, R.layout.match_list_item, matches);

   	        setListAdapter(matchAdapter);
   	        registerForContextMenu(getListView());
   		}
   	};
}
