package com.example.soccerman;

import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
import library.Constants;
import library.DatabaseHandler;
import library.UserFunctions;
 
public class LoginActivity extends Activity
{
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
 
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
 
            public void onClick(View view)
            {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(email.equals("") || password.equals(""))
                {
                	loginErrorMsg.setText("Incorrect username/password");
                	return;
                }
                
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.loginUser(email, password);
 
                try
                {
                    if (json.getString(Constants.KEY_SUCCESS) != null)
                    {
                        loginErrorMsg.setText("");
                        String res = json.getString(Constants.KEY_SUCCESS); 
                        if(Integer.parseInt(res) == 1)
                        {
                            // Store user details in SQLite Database
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
                            loginErrorMsg.setText("Incorrect username/password");
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
 
        btnLinkToRegister.setOnClickListener(new View.OnClickListener()
        {
 
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}