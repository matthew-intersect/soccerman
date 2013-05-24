package com.example.soccerman;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import library.MatchFunctions;
import library.UserFunctions;
import models.Match;
import adapters.MatchAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
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
public class MatchListActivity extends ListActivity
{
	private ArrayList<Match> matches = new ArrayList<Match>();
	private Runnable viewParts;
	private MatchAdapter matchAdapter;
	Button btnBack, btnYes, btnNo;
	private int teamId;
	
	private static String KEY_SUCCESS = "success";
	
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
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.change_attendance:
			final Dialog dialog = new Dialog(MatchListActivity.this);
			dialog.setTitle("Are you playing?");
			dialog.setContentView(R.layout.change_attendance);
			
			final MatchFunctions matchFunctions = new MatchFunctions();
			btnYes = (Button) dialog.findViewById(R.id.attend_yes);
			btnNo = (Button) dialog.findViewById(R.id.attend_no);
			final String currentUser = new UserFunctions().getLoggedInUserId(getApplicationContext());
			
			btnYes.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					JSONObject json = matchFunctions.addAttendance(currentUser, matches.get(info.position).getId(), 1); // 1 for yes
					try
                	{
                		if (json.getString(KEY_SUCCESS) != null) 
                		{
                			if(Integer.parseInt(json.getString(KEY_SUCCESS)) == 1)
                			{
                				Toast.makeText(MatchListActivity.this, "Attendance recorded successfully", Toast.LENGTH_LONG).show();
            					dialog.dismiss();
                			}
                			else
                			{
                				Toast.makeText(MatchListActivity.this, "Error occurred. Please try again later", Toast.LENGTH_LONG).show();
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
					JSONObject json = matchFunctions.addAttendance(currentUser, matches.get(info.position).getId(), 0); // 0 for no
					try
                	{
                		if (json.getString(KEY_SUCCESS) != null) 
                		{
                			if(Integer.parseInt(json.getString(KEY_SUCCESS)) == 1)
                			{
                				Toast.makeText(MatchListActivity.this, "Attendance recorded successfully", Toast.LENGTH_LONG).show();
            					dialog.dismiss();
                			}
                			else
                			{
                				Toast.makeText(MatchListActivity.this, "Error occurred. Please try again later", Toast.LENGTH_LONG).show();
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
	    case R.id.view_attendance:
	    	Intent matchList = new Intent(MatchListActivity.this, ViewAttendanceListActivity.class);
	    	matchList.putExtra("matchId", matches.get(info.position).getId());
	    	matchList.putExtra("teamId", teamId);
	    	startActivity(matchList);
	    	finish();
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
