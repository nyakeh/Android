package com.example.gauge;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class CalculationResultActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = this.getIntent().getExtras();		
		View resultsView = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_calculation_result, null);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
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
		
		setContentView(resultsView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculation_result, menu);
		return true;
	}

}
