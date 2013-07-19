package com.example.soccerman;

import org.json.JSONException;
import org.json.JSONObject;
 
import library.DatabaseHandler;
import library.UserFunctions;
import library.Constants;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegisterActivity extends Activity
{
    Button btnRegister, btnLinkToLogin;
    EditText inputFullName, inputEmail, inputPassword, inputConfirmPassword;
    TextView registerErrorMsg;
 
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
 
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        inputConfirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
         
        btnRegister.setOnClickListener(new View.OnClickListener()
        {         
            public void onClick(View view)
            {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirmPassword = inputConfirmPassword.getText().toString().trim();
                
                StringBuilder errors = new StringBuilder();
                if(name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals(""))
                {
                	errors.append("Name, email and password cannot be blank\n");
                	if(name.equals(""))
                		inputFullName.setText("");
                	if(email.equals(""))
                		inputEmail.setText("");
                	if(password.equals(""))
                		inputPassword.setText("");
                	if(confirmPassword.equals(""))
                		inputConfirmPassword.setText("");
                }
                if(!password.equals(confirmPassword))
                {
                	errors.append("Passwords don't match\n");
                }
                
                if(errors.toString() != "")
                {
                	registerErrorMsg.setText(errors.toString());
                	return;
                }
                
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(name, email, password);
                 
                try
                {
                    if (json.getString(Constants.KEY_SUCCESS) != null) 
                    {
                        registerErrorMsg.setText("");
                        String res = json.getString(Constants.KEY_SUCCESS); 
                        if(Integer.parseInt(res) == 1)
                        {
                            // Store user details in local database 
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
                             
                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json.getInt(Constants.KEY_ID), json_user.getString(Constants.KEY_NAME),
                            		json_user.getString(Constants.KEY_EMAIL), json_user.getString(Constants.KEY_CREATED_AT));                        
                            Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                            finish();
                        }
                        else
                        {
                        	if(Integer.parseInt(json.getString(Constants.KEY_ERROR)) == 2)
                        	{
                        		registerErrorMsg.setText("User with given email already exists");
                        		return;
                        	}
                        	registerErrorMsg.setText(Constants.BASIC_ERROR);
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
 
        btnLinkToLogin.setOnClickListener(new View.OnClickListener()
        {
 
            public void onClick(View view)
            {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }
}