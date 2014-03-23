package com.example.gauge;

import java.util.UUID;

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

public class CompareActivity extends DrawerActivity {
	SharedPreferences prefs;
	Button calculateBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_compare);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);	
		
		calculateBtn = ( Button ) findViewById(R.id.btn_calculate);		
		calculateBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  calculateBtn.setClickable(false);
		    	  EditText house_value = (EditText) findViewById(R.id.fld_house_value);
		    	  EditText deposit = (EditText) findViewById(R.id.fld_deposit);
		    	  EditText term = (EditText) findViewById(R.id.fld_term);
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
		    	  Intent intent = new Intent(CompareActivity.this, ComparisonResultActivity.class);    	  
		    	  startActivity(intent);
		    	  //new AsyncHttpRequest(CompareActivity.this).Compare(house_value.getText().toString(),deposit.getText().toString(),term.getText().toString(),accountId,customerReference);
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compare, menu);
		return true;
	}

	@Override
	public void handleResponse(GaugeHttpResponse response) {
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
