package com.example.gauge;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity  extends Activity implements IGaugeAsync {
	Button registerBtn;
	SharedPreferences prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
		
		String username = getIntent().getStringExtra("Username");
		View resultsView = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_register, null);
		
		TextView fld_email = (TextView) resultsView.findViewById(R.id.fld_email);
		fld_email.setText(username);

		setContentView(resultsView);
		setupActionBar(); 
		registerBtn = ( Button ) findViewById(R.id.btn_register);		
		registerBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  registerBtn.setClickable(false);
		    	  EditText email = (EditText) findViewById(R.id.fld_email);
		    	  if(!email.getText().toString().toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {
	  			  		Toast toast = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG);
		  				toast.show();
		  		  	    registerBtn.setClickable(true);
		  				return;
		    	  }
		    	  EditText forename = (EditText) findViewById(R.id.fld_forename);
		    	  EditText surname = (EditText) findViewById(R.id.fld_surname);
		    	  EditText password = (EditText) findViewById(R.id.fld_pwd);
		    	  new AsyncHttpRequest(RegisterActivity.this).Register(email.getText().toString(),forename.getText().toString(),surname.getText().toString(),password.getText().toString());
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_settings:
				Intent intent = new Intent(RegisterActivity.this, SettingActivity.class);
				startActivity(intent);
	        	return true;				
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void handleResponse(GaugeHttpResponse response) {
		Toast toast = Toast.makeText(getApplicationContext(), "Error, Try again", Toast.LENGTH_LONG);
		if (response.statusCode == 201) {
			try {
				JSONObject jsonResult = new JSONObject(response.content);
				Editor edit = prefs.edit();
				int accountId = Integer.parseInt(jsonResult.get("AccountId").toString());
				String forename = jsonResult.get("Forename").toString();
				edit.putInt("AccountId", accountId);
				edit.putString("Forename", forename);
				edit.commit();
				toast = Toast.makeText(getApplicationContext(), "Welcome to Gauge " + forename, Toast.LENGTH_LONG);
			} catch (JSONException e) {
				Log.d("Json parse exception", e.getMessage());
			}
	  	    Intent intent = new Intent(RegisterActivity.this, CalculateActivity.class);		    	  
		    startActivity(intent);
		}
  	  	toast.show();
  	    registerBtn.setClickable(true);
	}
}
