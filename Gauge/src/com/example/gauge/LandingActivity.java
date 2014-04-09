package com.example.gauge;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LandingActivity extends DrawerActivity {
	Boolean loggedIn = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_landing);
		
		SharedPreferences prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
		if(prefs.getInt("AccountId", 0) != 0) {
			TextView accountBtn = (TextView) findViewById(R.id.landing_login_label);
			accountBtn.setText("Account");
			loggedIn = true;
		}
		
		LinearLayout calculate = (LinearLayout) findViewById(R.id.landing_calculate);
        calculate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                	Intent intent = new Intent(LandingActivity.this, CalculateActivity.class);
				startActivity(intent);
            }
        });
		
		LinearLayout compare = (LinearLayout) findViewById(R.id.landing_compare);
        compare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(LandingActivity.this, CompareActivity.class);
				startActivity(intent);
            }
        });
		
		LinearLayout login = (LinearLayout) findViewById(R.id.landing_login);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent;
            	if(loggedIn) {
                	intent = new Intent(LandingActivity.this, AccountActivity.class);
            	} else {
            		intent = new Intent(LandingActivity.this, MainActivity.class);
            	}
				startActivity(intent);
            }
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(prefs.getInt("AccountId", 0) != 0) {
			getMenuInflater().inflate(R.menu.main, menu);
		} else {
			getMenuInflater().inflate(R.menu.guest, menu);			
		}
		return true;
	}

}
