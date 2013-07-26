package com.example.soccerman;

import org.json.JSONException;
import org.json.JSONObject;

import library.Constants;
import library.UserFunctions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity
{
	Button btnChangePassword, btnCancel;
	EditText inputOldPassword, inputNewPassword,inputConfirmNewPassword;
	TextView changePasswordErrorMsg;

	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        
        btnChangePassword = (Button) findViewById(R.id.btnSavePassword);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        inputOldPassword = (EditText) findViewById(R.id.old_password);
        inputNewPassword = (EditText) findViewById(R.id.new_password);
        inputConfirmNewPassword = (EditText) findViewById(R.id.confirm_new_password);
        changePasswordErrorMsg = (TextView) findViewById(R.id.change_password_error);

        btnChangePassword.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				String oldPassword = inputOldPassword.getText().toString().trim();
				String newPassword = inputNewPassword.getText().toString().trim();
				String confirmNewPassword = inputConfirmNewPassword.getText().toString().trim();
				
				UserFunctions userFunctions = new UserFunctions();
				String userEmail = userFunctions.getLoggedInUserEmail(getApplicationContext());
				JSONObject checkOldPassword = userFunctions.loginUser(userEmail, oldPassword);
				
				try
				{
					if (checkOldPassword.getString(Constants.KEY_SUCCESS) != null
							&& Integer.parseInt(checkOldPassword.getString(Constants.KEY_SUCCESS)) == 1)
					{
						if(newPassword.equals(confirmNewPassword))
						{
							if(newPassword.length() < 6 || newPassword.length() > 20)
							{
								changePasswordErrorMsg.setText(Constants.PASSWORD_LENGTH_ERROR);
								return;
							}
							JSONObject json = userFunctions.changeUserPassword(userFunctions.getLoggedInUserId(getApplicationContext()),
								newPassword);
							if(json.getString(Constants.KEY_SUCCESS) != null
									&& Integer.parseInt(json.getString(Constants.KEY_SUCCESS)) == 1)
							{
								Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_LONG).show();
								
								Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
								startActivity(dashboard);
	                            finish();
							}
							else
							{
								changePasswordErrorMsg.setText(Constants.BASIC_ERROR);
							}
						}
						else
						{
							changePasswordErrorMsg.setText(Constants.NEW_PASSWORD_ERROR);
						}
					}
					else
					{
						changePasswordErrorMsg.setText(Constants.OLD_PASSWORD_ERROR);
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		});

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
 
            public void onClick(View view)
            {
                Intent home = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(home);
                finish();
            }
        });
	}
}
