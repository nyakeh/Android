package com.example.gauge;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountActivity extends DrawerActivity implements IGaugeAsync {
	SharedPreferences prefs;
	Button updateBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_account);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
		// Async GET /Account request
		
		// Update button PUT
		updateBtn = ( Button ) findViewById(R.id.btn_register);		
		updateBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  updateBtn.setClickable(false);
		    	  EditText email = (EditText) findViewById(R.id.fld_account_email);
		    	  if(!email.getText().toString().toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {
	  			  		Toast toast = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG);
		  				toast.show();
		  				updateBtn.setClickable(true);
		  				return;
		    	  }
		    	  EditText forename = (EditText) findViewById(R.id.fld_account_forename);
		    	  EditText surname = (EditText) findViewById(R.id.fld_account_surname);
		    	  EditText password = (EditText) findViewById(R.id.fld_account_password);
		    	  int accountId = prefs.getInt("AccountId", 0);
		    	  new AsyncHttpRequest(AccountActivity.this).UpdateAccount(accountId,email.getText().toString(),forename.getText().toString(),surname.getText().toString(),password.getText().toString());
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
	public void handleResponse(GaugeHttpResponse results) {
		// fill form with results set	
	}

}
