package com.example.gauge;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity  extends DrawerActivity {
	Button registerBtn;
	SharedPreferences prefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
		
		String username = getIntent().getStringExtra("Username");
		View resultsView = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_register, null);
		
		TextView fld_username = (TextView) resultsView.findViewById(R.id.fld_username);
		fld_username.setText(username);
		buildSideNavigation(resultsView);
		
		registerBtn = ( Button ) findViewById(R.id.btn_register);		
		registerBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  registerBtn.setClickable(false);
		    	  EditText username = (EditText) findViewById(R.id.fld_username);
		    	  EditText forename = (EditText) findViewById(R.id.fld_forename);
		    	  EditText surname = (EditText) findViewById(R.id.fld_surname);
		    	  EditText password = (EditText) findViewById(R.id.fld_pwd);
		    	  new AsyncHttpRequest(RegisterActivity.this).Register(username.getText().toString(),forename.getText().toString(),surname.getText().toString(),password.getText().toString());
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	@Override
	public void handleResponse(GaugeHttpResponse response) {
		Toast toast = Toast.makeText(getApplicationContext(), "Error, Try again", Toast.LENGTH_LONG);
		if (response.statusCode == 201) {
			try {
				JSONObject jsonResult = new JSONObject(response.content);
				Editor edit = prefs.edit();
				String accountId = (String) jsonResult.get("AccountId").toString();
				String forename = (String) jsonResult.get("Forename");
				edit.putString("AccountId", accountId);
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
