package com.example.gauge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ComparisonResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comparison_result);
		setupActionBar(); 
		Bundle extras = this.getIntent().getExtras();
		String resultsSet = extras.getString("Json_Response");
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.comparison_result_layout);
		try {
			JSONArray jsonArray = new JSONArray(resultsSet); 
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject calculation = jsonArray.getJSONObject(i);
				String bank = calculation.get("Bank").toString();				
				String interestRate = calculation.get("InterestRate").toString();				
				String monthlyRepayment = calculation.get("MonthlyRepayment").toString();				
				String totalPaid = calculation.get("TotalPaid").toString();				
				String totalInterest = calculation.get("TotalInterest").toString();	
				View compareResultView = LayoutInflater.from(getBaseContext()).inflate(R.layout.comparison_result, null);				
				TextView tv_bank = (TextView) compareResultView.findViewById(R.id.compare_bank);
				TextView tv_interest_rate = (TextView) compareResultView.findViewById(R.id.compare_interest_rate);
				TextView tv_monthly_repayment = (TextView) compareResultView.findViewById(R.id.compare_monthly_repayment);
				TextView tv_total_paid = (TextView) compareResultView.findViewById(R.id.compare_total_paid);
				TextView tv_total_interest = (TextView) compareResultView.findViewById(R.id.compare_total_interest);
				tv_bank.setText(bank);
				tv_interest_rate.setText(interestRate+"% APR");
				tv_monthly_repayment.setText(monthlyRepayment);
				tv_total_paid.setText(totalPaid);
				tv_total_interest.setText(totalInterest);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(10, 0, 10, 10);
				compareResultView.setLayoutParams(lp);
				linearLayout.addView(compareResultView);
			}
		} catch (JSONException e) {
			Log.d("Json parse exception - ComparisonResultActivity.java", e.getMessage());
		}
				
	}

	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comparison_results, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
