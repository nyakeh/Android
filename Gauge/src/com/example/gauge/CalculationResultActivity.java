package com.example.gauge;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculationResultActivity extends DrawerActivity {
	private AlertDialog.Builder alert;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = this.getIntent().getExtras();		
		View resultsView = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_calculation_result, null);
		
		
		TextView property_value = (TextView) resultsView.findViewById(R.id.property_value);
		TextView deposit = (TextView) resultsView.findViewById(R.id.deposit);
		TextView term = (TextView) resultsView.findViewById(R.id.term);
		TextView interest_rate = (TextView) resultsView.findViewById(R.id.interest_rate);
		TextView fees = (TextView) resultsView.findViewById(R.id.fees);

		property_value.setText(extras.getString("Property_value"));
		deposit.setText(extras.getString("Deposit"));
		term.setText(extras.getString("Term"));
		interest_rate.setText(extras.getString("Interest_rate"));
		fees.setText(extras.getString("Fees"));

		
		buildSideNavigation(resultsView);

		Button editBtn = ( Button ) findViewById(R.id.btn_edit);		
		editBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  finish();
		      }
		});
		
		Button favouriteBtn = ( Button ) findViewById(R.id.btn_favourite);		
		favouriteBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Toast toast = Toast.makeText(getApplicationContext(), "Calculation saved into favourites.", Toast.LENGTH_LONG);
		    	  toast.show();
		      }
		});  
   	    
		Button emailBtn = ( Button ) findViewById(R.id.btn_email);		
		emailBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  alert = new AlertDialog.Builder(CalculationResultActivity.this);
		     	    final EditText editText = new EditText(CalculationResultActivity.this);
		     	    editText.setHint("email@address.com");
		     	    alert.setTitle("Share with friend");
		     	    alert.setView(editText);
		     	    alert.setPositiveButton("Send", new DialogInterface.OnClickListener(){ 
		  		  public void onClick(DialogInterface dialog, int btn) { 
		  			  // String emailAddress = editText.getText().toString();
		  			  // Null check & call email Async function
		  		  } });
		     	    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int btn) {  } });
		    	  alert.show();
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculation_result, menu);
		return true;
	}

}
