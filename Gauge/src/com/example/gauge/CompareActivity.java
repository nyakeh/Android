package com.example.gauge;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CompareActivity extends DrawerActivity implements IGaugeAsync {
	SharedPreferences prefs;
	Button calculateBtn;
	AlertDialog alert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_compare);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);	
		
		calculateBtn = ( Button ) findViewById(R.id.btn_calculate);		
		calculateBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  EditText ET_house_value = (EditText) findViewById(R.id.fld_compare_house_value);
		    	  EditText ET_deposit = (EditText) findViewById(R.id.fld_compare_deposit);
		    	  EditText ET_term = (EditText) findViewById(R.id.fld_compare_term);
		    	  String house_value = ET_house_value.getText().toString();
		    	  String deposit = ET_deposit.getText().toString();
		    	  String term = ET_term.getText().toString();
		    	  if(inputValid(house_value, deposit, term)) {
			    	  calculateBtn.setClickable(false);
			    	  new AsyncHttpRequest(CompareActivity.this).Compare(house_value,deposit,term,0,UUID.fromString("00000000-0000-0000-0000-000000000000"));
			    	  createPopUp();
		    	  }
		      }
		});
	}
	
	private Boolean inputValid(String house_value, String deposit, String term) {
		ArrayList<String> invalidFields = new ArrayList<String>();
		
		if(house_value.isEmpty()) {
			invalidFields.add("House value");
		}
		if(deposit.isEmpty()) {
			invalidFields.add("Deposit");
		}
		if(term.isEmpty()) {
			invalidFields.add("Term");
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
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CompareActivity.this);
  	  	alert = alertBuilder.create();
  	  	alert.setTitle("Calculating");
  	  	alert.setCancelable(false);
  	  	alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compare, menu);
		return true;
	}

	@Override
	public void handleResponse(GaugeHttpResponse response) {
		alert.cancel();
		if (response.statusCode == 201) {
			Intent intent = new Intent(CompareActivity.this, ComparisonResultActivity.class);
			intent.putExtra("Json_Response", response.content);		    	  
			startActivity(intent);
	    } else {
			Toast toast = Toast.makeText(getApplicationContext(), "Error, Try again", Toast.LENGTH_LONG);
			toast.show();	    	
	    }
  	  	calculateBtn.setClickable(true);
	}

}
