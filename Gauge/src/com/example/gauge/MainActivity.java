package com.example.gauge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends DrawerActivity implements IGaugeAsync {	
	SharedPreferences prefs;
	Button loginBtn;
	AlertDialog alert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_main);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
		
		//if(prefs.getString("AccountId", null) != null) {} // if logged in... move forward to landing page
		
		loginBtn = ( Button ) findViewById(R.id.btn_login);		
		loginBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  EditText ET_username = (EditText) findViewById(R.id.fld_username);
		    	  EditText ET_password = (EditText) findViewById(R.id.fld_pwd);
		    	  String username = ET_username.getText().toString();
		    	  String password = ET_password.getText().toString();
		    	  
		    	  if(inputValid(username, password)){
			    	  loginBtn.setClickable(false);
			    	  new AsyncHttpRequest(MainActivity.this).Login(username,password);
			    	  createPopUp();		    		  
		    	  }
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
	
	private Boolean inputValid(String username, String password) {
		ArrayList<String> invalidFields = new ArrayList<String>();
		
		if(!username.toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {
			invalidFields.add("Username");
		}
		if(password.isEmpty()) {
			invalidFields.add("Password");
		}
		if(invalidFields.size() > 0) {
			String message = buildErrorMessage(invalidFields);
			Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
			toast.show();
			return false;
		}
		return true;
	}
	
	private void createPopUp() {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
  	  alert = alertBuilder.create();
  	  alert.setTitle("Authenticating");
  	  alert.setCancelable(false);
  	  alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(prefs.getInt("AccountId", 0) != 0) {
			getMenuInflater().inflate(R.menu.main, menu);
		} else {
			getMenuInflater().inflate(R.menu.guest, menu);			
		}
		return true;
	}
	
	@Override
	public void handleResponse(GaugeHttpResponse response) {
		alert.cancel();
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
