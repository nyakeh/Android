package com.example.gauge;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends DrawerActivity {
	Button loginBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_main);
		
		loginBtn = ( Button ) findViewById(R.id.btn_login);		
		loginBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  loginBtn.setClickable(false);
		    	  new AsyncUserLogin(MainActivity.this).execute();
		      }
		});

		Button skipBtn = ( Button ) findViewById(R.id.btn_skip);		
		skipBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(MainActivity.this, CalculateActivity.class);
		    	  startActivity(intent);
		      }
		});
		
		Button register_btn = ( Button ) findViewById(R.id.btn_register);		
		register_btn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
		    	  EditText username = (EditText) findViewById(R.id.fld_username);
		 	      intent.putExtra("Username", username.getText().toString());
		    	  startActivity(intent);
		      }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	

	public void handleResponse(String response) {
		Toast toast = Toast.makeText(getApplicationContext(), "Stacked It", Toast.LENGTH_LONG);
		if (response!=null) {
			toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
		}
  	  	toast.show();
  	  	loginBtn.setClickable(true);
  	  //Intent intent = new Intent(MainActivity.this, CalculateActivity.class);		    	  
	  //startActivity(intent);
	}
}
