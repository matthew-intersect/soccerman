package com.example.soccerman;

import library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddMatchActivity extends Activity
{
	private UserFunctions userFunctions;
	private Button btnCancelAdd;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())) 
        {
        	setContentView(R.layout.add_match);
        	
        	Bundle extras = getIntent().getExtras();
    	    int team = extras.getInt("teamId");
    	    
    	    btnCancelAdd = (Button) findViewById(R.id.btnCancelAddMatch);
    	    
    	    System.out.println(team);
    	    
    	    btnCancelAdd.setOnClickListener(new View.OnClickListener()
            {
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
}