package com.example.soccerman;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import library.Constants;
import library.MatchFunctions;
import models.PlayerAttendance;
import models.TeamRole;

import adapters.PlayerAttendanceAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
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
public class ViewAttendanceListActivity extends ListActivity
{
	private ArrayList<PlayerAttendance> playerAttendances = new ArrayList<PlayerAttendance>();
	private int matchId, teamId;
	private Runnable viewParts;
	private PlayerAttendanceAdapter playerAttendanceAdapter;
	private Button btnBack;
	private String teamHomeGround;
	private TeamRole teamRole;

	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_attendance_list);
		
		Bundle extras = getIntent().getExtras();
	    matchId = extras.getInt("matchId");
	    teamId = extras.getInt("teamId");
	    teamHomeGround = extras.getString("teamHomeGround");
	    teamRole = extras.getParcelable("teamRole");
		
		btnBack = (Button) findViewById(R.id.back_to_matches);
		playerAttendanceAdapter = new PlayerAttendanceAdapter(this, R.layout.player_attendance_list_item, playerAttendances);
		setListAdapter(playerAttendanceAdapter);
		
		btnBack.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				Intent matches = new Intent(ViewAttendanceListActivity.this, MatchListActivity.class);
				matches.putExtra("teamId", teamId);
				matches.putExtra("teamHomeGround", teamHomeGround);
				matches.putExtra("teamRole", (Parcelable) teamRole);
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
	
	public void onListItemClick(ListView l, View v, int pos, long id)
	{
		l.showContextMenuForChild(v);
	}
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.view_attendance_menu, menu);
	    
	    if(teamRole.equals(TeamRole.PLAYER)) 
	    {
	    	menu.getItem(0).setVisible(false);
	    }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) 
		{
			case R.id.set_attendance:
				final Dialog dialog = new Dialog(ViewAttendanceListActivity.this);
				dialog.setTitle("Is this player playing?");
				dialog.setContentView(R.layout.change_attendance);
				
				final MatchFunctions matchFunctions = new MatchFunctions();
				Button btnYes = (Button) dialog.findViewById(R.id.attend_yes);
				Button btnNo = (Button) dialog.findViewById(R.id.attend_no);
				final int playerId = playerAttendances.get(info.position).getPlayerId();
				
				btnYes.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						JSONObject json = matchFunctions.addAttendance(String.valueOf(playerId), matchId, 1); // 1 for yes
						try
	                	{
	                		if (json.getString(Constants.KEY_SUCCESS) != null) 
	                		{
	                			if(Integer.parseInt(json.getString(Constants.KEY_SUCCESS)) == 1)
	                			{
	                				Toast.makeText(ViewAttendanceListActivity.this, "Attendance recorded successfully", Toast.LENGTH_LONG).show();
	            					dialog.dismiss();
	            					finish();
	            					startActivity(getIntent());
	                			}
	                			else
	                			{
	                				Toast.makeText(ViewAttendanceListActivity.this, "Error occurred. Please try again later", Toast.LENGTH_LONG).show();
	            					dialog.dismiss();
	                			}
	                		}
	                	}
						catch (JSONException e) 
	                	{
	            			e.printStackTrace();
	            		}
					}
				});
				
				btnNo.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						JSONObject json = matchFunctions.addAttendance(String.valueOf(playerId), matchId, 0); // 0 for no
						try
	                	{
	                		if (json.getString(Constants.KEY_SUCCESS) != null) 
	                		{
	                			if(Integer.parseInt(json.getString(Constants.KEY_SUCCESS)) == 1)
	                			{
	                				Toast.makeText(ViewAttendanceListActivity.this, "Attendance recorded successfully", Toast.LENGTH_LONG).show();
	            					dialog.dismiss();
	            					finish();
	            					startActivity(getIntent());
	                			}
	                			else
	                			{
	                				Toast.makeText(ViewAttendanceListActivity.this, "Error occurred. Please try again later", Toast.LENGTH_LONG).show();
	            					dialog.dismiss();
	                			}
	                		}
	                	}
						catch (JSONException e) 
	                	{
	            			e.printStackTrace();
	            		}
					}
				});
				
				dialog.show();
				return true;
	    }
	    return false;
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
