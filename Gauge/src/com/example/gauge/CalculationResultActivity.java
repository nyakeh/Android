package com.example.gauge;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculationResultActivity extends Activity implements IGaugeAsync{
	private AlertDialog.Builder alert;
	SharedPreferences prefs;
	Bundle extras;
	int calculationId;
	Boolean loggedIn = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
		
		extras = this.getIntent().getExtras();		
		calculationId = extras.getInt("Calculation_Id");
		View resultsView = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_calculation_result, null);
		
		TextView monthly_Repayment = (TextView) resultsView.findViewById(R.id.monthly_repayment);
		TextView loan_To_Value = (TextView) resultsView.findViewById(R.id.ltv);
		TextView total_Paid = (TextView) resultsView.findViewById(R.id.total_paid);
		TextView total_interest = (TextView) resultsView.findViewById(R.id.total_interest);
		TextView property_value = (TextView) resultsView.findViewById(R.id.property_value);
		TextView deposit = (TextView) resultsView.findViewById(R.id.deposit);
		TextView term = (TextView) resultsView.findViewById(R.id.term);
		TextView interest_rate = (TextView) resultsView.findViewById(R.id.interest_rate);
		TextView fees = (TextView) resultsView.findViewById(R.id.fees);

		monthly_Repayment.setText(extras.getString("Monthly_Repayment"));
		loan_To_Value.setText(extras.getString("Loan_To_Value")+"%");
		total_Paid.setText(extras.getString("Total_Paid"));
		total_interest.setText(extras.getString("Total_Interest"));
		property_value.setText(extras.getString("House_Value"));
		deposit.setText(extras.getString("Deposit"));
		term.setText(extras.getString("Term")+" years");
		interest_rate.setText(extras.getString("Interest_Rate")+"%");
		fees.setText(extras.getString("Fees"));

		setContentView(resultsView);
		setupActionBar(); 

		Button editBtn = (Button) findViewById(R.id.btn_edit);
		Button favouriteBtn = (Button) findViewById(R.id.btn_favourite);
		Button emailBtn = (Button) findViewById(R.id.btn_email);
		
		if(prefs.getInt("AccountId", 0) == 0) {
			favouriteBtn.setEnabled(false);
			favouriteBtn.getBackground().setAlpha(200);
			favouriteBtn.setTextColor(Color.DKGRAY);
			emailBtn.setEnabled(false);
			emailBtn.setAlpha(200);
			emailBtn.setTextColor(Color.DKGRAY);
			loggedIn = false;
		}
		
		editBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  finish();
		      }
		});		
			
		favouriteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncHttpRequest(CalculationResultActivity.this).Favourite(calculationId);
			}
		});  
   	    		
		emailBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alert = new AlertDialog.Builder(CalculationResultActivity.this);
				final EditText inputEmailAddress = new EditText(CalculationResultActivity.this);
				inputEmailAddress.setHint("email@address.com");
				alert.setTitle("Share with friend");
				alert.setView(inputEmailAddress);
				alert.setPositiveButton("Send", new DialogInterface.OnClickListener(){ 
		  		  	public void onClick(DialogInterface dialog, int btn) { 
		  		  		String emailAddress = inputEmailAddress.getText().toString().toLowerCase();	
		  		  		if(emailAddress.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")) {		  			  
		  		  			new AsyncHttpRequest(CalculationResultActivity.this).Email(calculationId, emailAddress);
		  			  	} else {
		  			  		Toast toast = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG);
			  				toast.show();
		  			  	}
		  		  	}});
	     	    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int btn) {  } });
	     	    alert.show();
			}
		});
	}
	
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculation_result, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_settings:
				Intent intent = new Intent(CalculationResultActivity.this, SettingActivity.class);
				startActivity(intent);
	        	return true;
				
		}
		return super.onOptionsItemSelected(item);
	}
		
	@Override
	public void handleResponse(GaugeHttpResponse response) {
		Toast toast = Toast.makeText(getApplicationContext(), "Error, Try again", Toast.LENGTH_LONG);
		if (response.statusCode == 200) {
			if(response.httpMethod == "PUT") {
				toast = Toast.makeText(getApplicationContext(), "Calculation saved to favourites.", Toast.LENGTH_LONG);				
			} else {
				toast = Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_LONG);				
			}
		}
  	  	toast.show();
	}

}
