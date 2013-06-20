package com.example.soccerman;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import library.Constants;
import library.MatchFunctions;
import library.UserFunctions;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddMatchActivity extends Activity
{
	private UserFunctions userFunctions;
	private EditText inputOpponent, inputVenue;
	private Button btnAddMatch, btnCancelAdd;
	private TextView dateTimeLabel, addMatchErrorMsg;
	private CheckBox checkHome;
	private Calendar dateTime = Calendar.getInstance();
	private String teamHomeGround;
	
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM - h:mm a");

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())) 
        {
        	setContentView(R.layout.add_match);
        	
        	Bundle extras = getIntent().getExtras();
    	    final int team = extras.getInt("teamId");
    	    teamHomeGround = extras.getString("teamHomeGround");
    	    
    	    inputOpponent = (EditText) findViewById(R.id.opponent);
    	    checkHome = (CheckBox) findViewById(R.id.chkHome);
    	    inputVenue = (EditText) findViewById(R.id.venue);
    	    btnAddMatch = (Button) findViewById(R.id.btnAddMatch);
    	    btnCancelAdd = (Button) findViewById(R.id.btnCancelAddMatch);
    	    dateTimeLabel = (TextView) findViewById(R.id.date_time_lbl);
    	    addMatchErrorMsg = (TextView) findViewById(R.id.add_match_error);
    	    
    	    dateTime.set(Calendar.HOUR_OF_DAY, 12);
			dateTime.set(Calendar.MINUTE, 0);
    	    updateDateTimeLabel();
    	    
    	    checkHome.setOnClickListener(new View.OnClickListener()
    	    {
				@Override
				public void onClick(View v)
				{
					if(checkHome.isChecked())
					{
						inputVenue.setText(teamHomeGround);
					}
				}
    	    });
    	    
    	    btnAddMatch.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					String opponent = inputOpponent.getText().toString().trim();
					String venue = inputVenue.getText().toString().trim();
					boolean home = checkHome.isChecked();
					String homeAway = (home) ? "home" : "away";
					long time = dateTime.getTimeInMillis();
					
					if(opponent.equals("") || venue.equals(""))
					{
						addMatchErrorMsg.setText("Opponent and venue can't be blank");
						if(opponent.equals(""))
							inputOpponent.setText("");
						if(venue.equals(""))
							inputVenue.setText("");
						return;
					}
					
					MatchFunctions matchFunctions = new MatchFunctions();
					JSONObject json = matchFunctions.addMatch(team, opponent, venue, homeAway, time);
					
					try
					{
						if (json.getString(Constants.KEY_SUCCESS) != null) 
						{
							String res = json.getString(Constants.KEY_SUCCESS); 
                			if(Integer.parseInt(res) == 1)
                			{
                				addMatchErrorMsg.setText("");
                				
                				Toast.makeText(AddMatchActivity.this, "Match added successfully", Toast.LENGTH_LONG).show();
                				
                				Intent dashboard = new Intent(AddMatchActivity.this, MyTeamListActivity.class);
                        		startActivity(dashboard);
                        		finish();
                			}
                			else 
	                		{
	                			addMatchErrorMsg.setText("Error adding match. PLease try again");
	                		}
						}
					}
					catch (JSONException e) 
                	{
            			e.printStackTrace();
            		}
				}
			});
    	    
    	    btnCancelAdd.setOnClickListener(new View.OnClickListener()
            {
    	    	@Override
            	public void onClick(View view) {
            		Intent dashboard = new Intent(AddMatchActivity.this, MyTeamListActivity.class);
            		startActivity(dashboard);
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
	
	public void setDate(View v)
	{
		new DatePickerDialog(AddMatchActivity.this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }
	
	public void setTime(View v)
	{
		new TimePickerDialog(AddMatchActivity.this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }
	
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
	{
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			dateTime.set(Calendar.YEAR, year);
			dateTime.set(Calendar.MONTH, monthOfYear);
			dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateDateTimeLabel();
		}
	};
	
	TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener()
	{
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateTime.set(Calendar.MINUTE,minute);
			updateDateTimeLabel();
		}
	};
	
	private void updateDateTimeLabel()
	{
		dateTimeLabel.setText(dateFormat.format(dateTime.getTime()));
	}
}