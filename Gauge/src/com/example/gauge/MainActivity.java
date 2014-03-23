package com.example.gauge;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends DrawerActivity {
	Button loginBtn;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_main);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
		
		//if(prefs.getString("AccountId", null) != null) {} // if logged in...
		
		loginBtn = ( Button ) findViewById(R.id.btn_login);		
		loginBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  loginBtn.setClickable(false);
		    	  EditText username = (EditText) findViewById(R.id.fld_username);
		    	  EditText password = (EditText) findViewById(R.id.fld_pwd);
		    	  new AsyncHttpRequest(MainActivity.this).Login(username.getText().toString(),password.getText().toString());
		      }
		});

		Button skipBtn = ( Button ) findViewById(R.id.btn_skip);		
		skipBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(MainActivity.this, CalculateActivity.class);
		    	  startActivity(intent);
		      }
		});
		
		Button register_btn = ( Button ) findViewById(R.id.btn_register);		
		register_btn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
		    	  EditText username = (EditText) findViewById(R.id.fld_username);
		 	      intent.putExtra("Username", username.getText().toString());
		    	  startActivity(intent);
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void handleResponse(GaugeHttpResponse response) {
		Toast toast = Toast.makeText(getApplicationContext(), "Error, Try again", Toast.LENGTH_LONG);
		if (response.statusCode == 200 || response.statusCode == 201) {
			try {
				JSONObject jsonResult = new JSONObject(response.content);
				int accountId = Integer.parseInt(jsonResult.get("AccountId").toString());
				String forename = jsonResult.get("Forename").toString();
				Editor edit = prefs.edit();
				edit.putInt("AccountId", accountId);
				edit.putString("Forename", forename);
				edit.commit();
				toast = Toast.makeText(getApplicationContext(), "Welcome back " + forename, Toast.LENGTH_LONG);
			} catch (JSONException e) {
				Log.d("Json parse exception", e.getMessage());
			}			
	  	    Intent intent = new Intent(MainActivity.this, CalculateActivity.class);		    	  
		    startActivity(intent);
		}
  	  	toast.show();
  	  	loginBtn.setClickable(true);
	}
}
