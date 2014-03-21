package com.example.gauge;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CompareActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_compare);
		
		Button _loginBtn = ( Button ) findViewById(R.id.btn_calculate);		
		_loginBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(CompareActivity.this, CalculationResultActivity.class);
		    	  EditText property_value = (EditText) findViewById(R.id.fld_property_value);
		    	  EditText deposit = (EditText) findViewById(R.id.fld_deposit);
		    	  EditText term = (EditText) findViewById(R.id.fld_term);
		 	      intent.putExtra("Property_value", property_value.getText().toString());
		 	      intent.putExtra("Deposit", deposit.getText().toString());
		 	      intent.putExtra("Term", term.getText().toString());
		    	  startActivity(intent);
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compare, menu);
		return true;
	}

}
