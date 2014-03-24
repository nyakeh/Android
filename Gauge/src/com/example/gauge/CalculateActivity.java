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

public class CalculateActivity extends DrawerActivity  implements IGaugeAsync{
	SharedPreferences prefs;
	Button calculateBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_calculate);	
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);	
		
		calculateBtn = ( Button ) findViewById(R.id.btn_calculate);		
		calculateBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  calculateBtn.setClickable(false);
		    	  EditText house_value = (EditText) findViewById(R.id.fld_house_value);
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
		    	  
		    	  new AsyncHttpRequest(CalculateActivity.this).Calculate(house_value.getText().toString(),deposit.getText().toString(),term.getText().toString(),interest_rate.getText().toString(),fees.getText().toString(),accountId,customerReference);
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculate, menu);
		return true;
	}

	@Override
	public void handleResponse(GaugeHttpResponse response) {
		if (response.statusCode == 201) {
			Intent intent = new Intent(CalculateActivity.this, CalculationResultActivity.class);
			try {
				JSONObject jsonResult = new JSONObject(response.content);
				int calculationId = Integer.parseInt(jsonResult.get("CalculationId").toString());
				String monthlyRepayment = jsonResult.get("MonthlyRepayment").toString();
				String loanToValue = jsonResult.get("LoanToValue").toString();
				String totalPaid = jsonResult.get("TotalPaid").toString();
				String totalInterest = jsonResult.get("TotalInterest").toString();
				String houseValue = jsonResult.get("HouseValue").toString();
				String deposit = jsonResult.get("Deposit").toString();
				String term = jsonResult.get("Term").toString();
				String fees = jsonResult.get("Fees").toString();
				String interestRate = jsonResult.get("InterestRate").toString();
	
				intent.putExtra("Calculation_Id", calculationId);
				intent.putExtra("Monthly_Repayment", monthlyRepayment);
				intent.putExtra("Loan_To_Value", loanToValue);
				intent.putExtra("Total_Paid", totalPaid);
				intent.putExtra("Total_Interest", totalInterest);	
				intent.putExtra("House_Value", houseValue);
				intent.putExtra("Deposit", deposit);
				intent.putExtra("Term", term);
				intent.putExtra("Fees", fees);	
				intent.putExtra("Interest_Rate", interestRate);	
			} catch (JSONException e) {
				Log.d("Json parse exception", e.getMessage());
			}  	    		    	  
			startActivity(intent);
	    } else {
			Toast toast = Toast.makeText(getApplicationContext(), "Error, Try again", Toast.LENGTH_LONG);
			toast.show();	    	
	    }
  	  	calculateBtn.setClickable(true);
	}
}
