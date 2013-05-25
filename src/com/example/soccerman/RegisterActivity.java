package com.example.soccerman;

import org.json.JSONException;
import org.json.JSONObject;
 
import library.DatabaseHandler;
import library.UserFunctions;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;
     
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_ID = "id";
    private static String KEY_CREATED_AT = "created_at";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
 
        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.registerName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
         
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                
                StringBuilder errors = new StringBuilder();
                if(name.equals(""))
                {
                	errors.append("Name cannot be blank\n");
                	inputFullName.setText("");
                }
                if(email.equals(""))
                {
                	errors.append("Email cannot be blank\n");
                	inputEmail.setText("");
                }
                if(password.equals(""))
                {
                	errors.append("Password cannot be blank\n");
                	inputPassword.setText("");
                }
                
                if(errors.toString() != "")
                {
                	registerErrorMsg.setText(errors.toString());
                	return;
                }
                
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(name, email, password);
                 
                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) 
                    {
                        registerErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS); 
                        if(Integer.parseInt(res) == 1)
                        {
                            // user successfully registered
                            // Store user details in local database 
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
                             
                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json.getInt(KEY_ID), json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_CREATED_AT));                        
                            Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                            finish();
                        }
                        else
                        {
                        	if(Integer.parseInt(json.getString(KEY_ERROR)) == 2)
                        	{
                        		registerErrorMsg.setText("User with given email already exists");
                        		return;
                        	}
                            registerErrorMsg.setText("Error occurred in registration. Try again later");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
 
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });
    }
}