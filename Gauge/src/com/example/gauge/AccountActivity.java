package com.example.gauge;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
	Button updateBtn;
	int accountId;
	EditText email;
	EditText forename;
	EditText surname;
	EditText password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_account);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);

		email = (EditText) findViewById(R.id.fld_account_email);
  	  	forename = (EditText) findViewById(R.id.fld_account_forename);
  	  	surname = (EditText) findViewById(R.id.fld_account_surname);
  	  	password = (EditText) findViewById(R.id.fld_account_password);
  	  	accountId = prefs.getInt("AccountId", 0);
		new AsyncHttpRequest(AccountActivity.this).RetrieveAccount(accountId);

		updateBtn = ( Button ) findViewById(R.id.btn_account_update);		
		updateBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  updateBtn.setClickable(false);
		    	  if(!email.getText().toString().toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {
	  			  		Toast toast = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG);
		  				toast.show();
		  				updateBtn.setClickable(true);
		  				return;
		    	  }
		    	  new AsyncHttpRequest(AccountActivity.this).UpdateAccount(accountId,email.getText().toString(),forename.getText().toString(),surname.getText().toString(),password.getText().toString());
		      }
		});
		
		Button logoutBtn = ( Button ) findViewById(R.id.btn_logout);		
		logoutBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  SharedPreferences.Editor editor = prefs.edit();
		    	  editor.clear();
		    	  editor.commit();
		    	  Intent intent = new Intent(AccountActivity.this, MainActivity.class);
		    	  startActivity(intent);
		    	  Toast toast = Toast.makeText(getApplicationContext(), "Logout successfull", Toast.LENGTH_LONG);
		    	  toast.show();
	    	  }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

	@Override
	public void handleResponse(GaugeHttpResponse response) {
		if (response.statusCode == 200) {
			if(response.httpMethod == "PUT") {
				Toast toast = Toast.makeText(getApplicationContext(), "Account details updated", Toast.LENGTH_LONG);
		  	  	toast.show();
		  	  	updateBtn.setClickable(false);	
			} else {
				try {
					JSONObject jsonResult = new JSONObject(response.content);
					email.setText(jsonResult.get("Email").toString());
					forename.setText(jsonResult.get("Forename").toString());
					surname.setText(jsonResult.get("Surname").toString());
				} catch (JSONException e) {
					Log.d("Json parse exception - AccountActivity.java", e.getMessage());
				}				
			}
		}
	}

}
