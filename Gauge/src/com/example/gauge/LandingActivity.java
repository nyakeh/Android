package com.example.gauge;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_landing);
		

		Button budgetBtn = ( Button ) findViewById(R.id.btn_budget);		
		budgetBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  //Intent intent = new Intent(LandingActivity.this, BudgetActivity.class);
		    	  //startActivity(intent);
		      }
		});
		Button calculateBtn = ( Button ) findViewById(R.id.btn_calculate);		
		calculateBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(LandingActivity.this, CalculateActivity.class);
		    	  startActivity(intent);
		      }
		});
		Button compareBtn = ( Button ) findViewById(R.id.btn_compare);		
		compareBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(LandingActivity.this, CompareActivity.class);
		    	  startActivity(intent);
		      }
		});
		Button borrowBtn = ( Button ) findViewById(R.id.btn_borrow);		
		borrowBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  //Intent intent = new Intent(LandingActivity.this, BorrowActivity.class);
		    	  //startActivity(intent);
		      }
		});
		Button loginBtn = ( Button ) findViewById(R.id.btn_login);		
		loginBtn.setOnClickListener(new View.OnClickListener() {
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
