package edu.unca.ivandryi;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "edu.unca.ivandryi.MESSAGE";
	String name = "", pass = "";
	TextView error;
	EditText username;
	EditText password;
	CheckBox check;
	Button login;
	Button forgotPassword;
	SharedPreferences app_preferences ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        error = (TextView)findViewById(R.id.error);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        check = (CheckBox)findViewById(R.id.check);
        login = (Button)findViewById(R.id.login);
        forgotPassword = (Button)findViewById(R.id.forgotPassword);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String str_user = app_preferences.getString("username","0" );
        String str_pass = app_preferences.getString("password", "0");
        String str_check = app_preferences.getString("checked", "no");
        if(str_check.equals("yes")) {
                username.setText(str_user);
                password.setText(str_pass);
                check.setChecked(true);
        }
        
        login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				name = username.getText().toString();
                pass = password.getText().toString();
                String str_check2 = app_preferences.getString("checked", "no");
                if(str_check2.equals("yes")) {
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putString("username", name);
                    editor.putString("password", pass);
                    editor.commit();
                }
     
				
				if(name.equals("") || pass.equals(""))
                {
					error.setText("Please enter username and password");
                }
                else {
                	ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    				postParameters.add(new BasicNameValuePair("username", username.getText().toString()));
    				postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
    				String response = null;
				try {
					response = CustomHttpClient.executeHttpPost("http://joe.cs.unca.edu/~andryiiv/senior/loginCheck.php",postParameters);
        			String result = response.toString();
           			result = result.replaceAll("\\s+","");
        			if (result.equals("0")) {
        				error.setText("Incorrect username or password");
        			} else {
        				moveToInventory();
        			}
				} catch (Exception e) {
					Log.e("log_tag","Error in http connection!!" + e.toString());
					}
                } 
			}
        	
        });
        
        check.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		SharedPreferences.Editor editor = app_preferences.edit();
                if (((CheckBox) v).isChecked()) {
                    editor.putString("checked", "yes");
                    editor.commit();
                }
                else {
                     editor.putString("checked", "no");
                     editor.commit();
                }
        	}
        });
        
        forgotPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = "";
				name = username.getText().toString();
				if(name.equals("")) {
					error.setText("Please Enter Username");
				} else {
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("username", username.getText().toString()));
					String response = "";
					try {
						response = CustomHttpClient.executeHttpPost("http://joe.cs.unca.edu/~andryiiv/senior/userCheck.php",postParameters);
	        			String result = response.toString();
	           			result = result.replaceAll("\\s+","");
	        			if (result.equals("0")) {
	        				error.setText("Incorrect username");
	        			} else {
	        				error.setText("Your password has been sent to " + result +  " email account");
	        			}
					} catch (Exception e) {
						Log.e("log_tag","Error in http connection!!" + e.toString());
						}
				}
			}
		});
    }
    public void moveToInventory(){
    	startActivity(new Intent(this, InventoryActivity.class));
    }
    
}
