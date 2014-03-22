package com.example.gauge;

import java.util.UUID;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CalculateActivity extends DrawerActivity {
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_calculate);	
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);	
		
		Button _loginBtn = ( Button ) findViewById(R.id.btn_calculate);		
		_loginBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  EditText property_value = (EditText) findViewById(R.id.fld_property_value);
		    	  EditText deposit = (EditText) findViewById(R.id.fld_deposit);
		    	  EditText term = (EditText) findViewById(R.id.fld_term);
		    	  EditText interest_rate = (EditText) findViewById(R.id.fld_interest_rate);
		    	  EditText fees = (EditText) findViewById(R.id.fld_fees);
		    	  int accountId = 0;
		    	  UUID customerReference = UUID.fromString("00000000-0000-0000-0000-000000000000");
		    	  if(prefs.getInt("AccountId", 0) != 0) {
		    		  accountId = prefs.getInt("AccountId", 0);
		    	  } else if(prefs.getString("CustomerReference", null) != null) {
		    		  customerReference = UUID.fromString(prefs.getString("CustomerReference", null));
		    	  } else {
		    		  customerReference = UUID.randomUUID();
		    		  Editor edit = prefs.edit();
		    		  edit.putString("CustomerReference", customerReference.toString());
		    		  edit.commit();
		    	  }
		    	  
		    	  new AsyncHttpRequest(CalculateActivity.this).Calculate(property_value.getText().toString(),deposit.getText().toString(),term.getText().toString(),interest_rate.getText().toString(),fees.getText().toString(),accountId,customerReference);
		    	  /*Intent intent = new Intent(CalculateActivity.this, CalculationResultActivity.class);
		 	      intent.putExtra("Property_value", property_value.getText().toString());
		 	      intent.putExtra("Deposit", deposit.getText().toString());
		 	      intent.putExtra("Term", term.getText().toString());
		 	      intent.putExtra("Interest_rate", interest_rate.getText().toString());
		 	      intent.putExtra("Fees", fees.getText().toString());
		    	  startActivity(intent);*/
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate, menu);
		return true;
	}
}
