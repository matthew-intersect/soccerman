package com.example.soccerman;

import models.TeamRole;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

public class EditLineupActivity extends Activity
{
	private int matchId, teamId;
	private String teamHomeGround;
	private TeamRole teamRole;
	private Button btnBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_lineup);
		
		Bundle extras = getIntent().getExtras();
	    matchId = extras.getInt("matchId");
	    teamId = extras.getInt("teamId");
	    teamHomeGround = extras.getString("teamHomeGround");
	    teamRole = extras.getParcelable("teamRole");
	    
	    btnBack = (Button) findViewById(R.id.back_to_matches);
	    
	    btnBack.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View view) 
			{
				Intent matches = new Intent(EditLineupActivity.this, MatchListActivity.class);
				matches.putExtra("teamId", teamId);
				matches.putExtra("teamHomeGround", teamHomeGround);
				matches.putExtra("teamRole", (Parcelable) teamRole);
				startActivity(matches);
				finish();
			}
		});
    }
}
