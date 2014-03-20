package com.example.gauge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity  extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		View resultsView = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_register, null);
		
		TextView username = (TextView) resultsView.findViewById(R.id.fld_username);
		username.setText(extras.getString("Username"));
		setContentView(resultsView);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
}
