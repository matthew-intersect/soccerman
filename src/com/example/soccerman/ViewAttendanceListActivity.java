package com.example.soccerman;

import java.util.ArrayList;

import library.MatchFunctions;
import models.PlayerAttendance;

import adapters.PlayerAttendanceAdapter;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

@SuppressLint("HandlerLeak")
public class ViewAttendanceListActivity extends ListActivity
{
	private ArrayList<PlayerAttendance> playerAttendances = new ArrayList<PlayerAttendance>();
	private int matchId, teamId;
	private Runnable viewParts;
	private PlayerAttendanceAdapter playerAttendanceAdapter;
	private Button btnBack;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_attendance_list);
		
		Bundle extras = getIntent().getExtras();
	    matchId = extras.getInt("matchId");
	    teamId = extras.getInt("teamId");
		
		btnBack = (Button) findViewById(R.id.back_to_matches);
		playerAttendanceAdapter = new PlayerAttendanceAdapter(this, R.layout.player_attendance_list_item, playerAttendances);
		setListAdapter(playerAttendanceAdapter);
		
		btnBack.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				Intent matches = new Intent(ViewAttendanceListActivity.this, MatchListActivity.class);
				matches.putExtra("teamId", teamId);
				startActivity(matches);
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
			MatchFunctions matchFunctions = new MatchFunctions();
			playerAttendances = matchFunctions.getMatchAttendance(String.valueOf(matchId));
   			
			playerAttendanceAdapter = new PlayerAttendanceAdapter(ViewAttendanceListActivity.this, R.layout.player_attendance_list_item, playerAttendances);

   	        setListAdapter(playerAttendanceAdapter);
   	        registerForContextMenu(getListView());
   		}
   	};
}
