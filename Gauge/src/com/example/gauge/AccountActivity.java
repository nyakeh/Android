package com.example.gauge;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountActivity extends DrawerActivity implements IGaugeAsync {
	SharedPreferences prefs;
	AlertDialog alert;
	Button updateBtn;
	int accountId;
	EditText ET_email;
	EditText ET_forename;
	EditText ET_surname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_account);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
		createPopUp("Retrieving account details");
  
		ET_email = (EditText) findViewById(R.id.fld_account_email);
  	  	ET_forename = (EditText) findViewById(R.id.fld_account_forename);
  	  	ET_surname = (EditText) findViewById(R.id.fld_account_surname);
  	  	accountId = prefs.getInt("AccountId", 0);
		new AsyncHttpRequest(AccountActivity.this).RetrieveAccount(accountId);
		
		updateBtn = ( Button ) findViewById(R.id.btn_account_update);		
		updateBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  if(!ET_email.getText().toString().toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {
	  			  		Toast toast = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG);
		  				toast.show();
		  				return;
		    	  }
		    	  String email = ET_email.getText().toString();
		    	  String forename = ET_forename.getText().toString();
		    	  String surname = ET_surname.getText().toString();
		    	  if(inputValid(email, forename, surname)){
			    	  updateBtn.setClickable(false);
			    	  createPopUp("Updating account details");
			    	  new AsyncHttpRequest(AccountActivity.this).UpdateAccount(accountId,email,forename,surname);
		    	  }
		      }
		});
		
		Button logoutBtn = ( Button ) findViewById(R.id.btn_logout);		
		logoutBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  logOutUser();
		    	  Toast toast = Toast.makeText(getApplicationContext(), "Logout successfull", Toast.LENGTH_LONG);
		    	  toast.show();
	    	  }
		});
	}
	
	private Boolean inputValid(String email, String forename, String surename) {
		ArrayList<String> invalidFields = new ArrayList<String>();
		
		if(email.isEmpty()) {
			invalidFields.add("Email");
		}
		if(forename.isEmpty()) {
			invalidFields.add("Forename");
		}
		if(surename.isEmpty()) {
			invalidFields.add("Surename");
		}
		if(invalidFields.size() > 0) {
			String message = buildErrorMessage(invalidFields);
			Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
			toast.show();
			return false;
		}
		return true;
	}
	
	private void createPopUp(String message) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AccountActivity.this);
  	  alert = alertBuilder.create();
  	  alert.setTitle(message);
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
		if (response.statusCode == 200) {
			if(response.httpMethod == "PUT") {
				Toast toast = Toast.makeText(getApplicationContext(), "Account details updated", Toast.LENGTH_LONG);
		  	  	toast.show();
		  	  	updateBtn.setClickable(true);	
			} else {
				try {
					JSONObject jsonResult = new JSONObject(response.content);
					ET_email.setText(jsonResult.get("Email").toString());
					ET_forename.setText(jsonResult.get("Forename").toString());
					ET_surname.setText(jsonResult.get("Surname").toString());
				} catch (JSONException e) {
					Log.d("Json parse exception - AccountActivity.java", e.getMessage());
				}				
			}
		}
	}

}
