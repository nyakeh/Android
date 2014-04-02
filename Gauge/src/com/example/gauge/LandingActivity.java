package com.example.gauge;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LandingActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_landing);
		
		LinearLayout iv = (LinearLayout) findViewById(R.id.landing_budget);
        iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(LandingActivity.this, MainActivity.class);
				startActivity(intent);
            }
        });
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing, menu);
		return true;
	}

}
