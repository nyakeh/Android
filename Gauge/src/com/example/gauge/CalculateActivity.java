package com.example.gauge;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CalculateActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculate);
		navigation(R.layout.activity_calculate);
		
		Button _loginBtn = ( Button ) findViewById(R.id.btn_calculate);		
		_loginBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(CalculateActivity.this, CalculationResultActivity.class);
		    	  EditText property_value = (EditText) findViewById(R.id.fld_property_value);
		    	  EditText deposit = (EditText) findViewById(R.id.fld_deposit);
		    	  EditText term = (EditText) findViewById(R.id.fld_term);
		    	  EditText interest_rate = (EditText) findViewById(R.id.fld_interest_rate);
		    	  EditText fees = (EditText) findViewById(R.id.fld_fees);
		 	      intent.putExtra("Property_value", property_value.getText().toString());
		 	      intent.putExtra("Deposit", deposit.getText().toString());
		 	      intent.putExtra("Term", term.getText().toString());
		 	      intent.putExtra("Interest_rate", interest_rate.getText().toString());
		 	      intent.putExtra("Fees", fees.getText().toString());
		    	  startActivity(intent);
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate, menu);
		/*String[] as = {"Mortgage", "Compare", "Budget", "Borrow", "Account"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, as);
		final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
		final ListView drawerList = (ListView) findViewById(R.id.drawer_list);
		// Set the adapter for the list view -- Uses a view to get fancy looking list items 
		drawerList.setAdapter(adapter);
        // Set the list's click listener
		drawerList.setOnItemClickListener(new OnItemClickListener()
		{			 
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, final int pos, long id)
	        {
	        	Intent intent;
	        	
	        	switch(pos) {
	        		case 1:
	        		case 2:
	        		case 3:
	        		case 4:
	        			intent = new Intent(CalculateActivity.this, MainActivity.class);
	        			break;
        			default:
        				drawer.closeDrawers();
        				return;
	        	}
	        	startActivity(intent);
	        	drawer.closeDrawers();
	        }
	    });*/
		return true;
	}

}
