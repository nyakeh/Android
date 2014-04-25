package com.example.gauge;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends DrawerActivity {
	Boolean loggedIn = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_setting);		
		
		if(prefs.getInt("AccountId", 0) != 0) {
			TextView accountBtn = (TextView) findViewById(R.id.btn_account);
			accountBtn.setText("Account");
			loggedIn = true;
		}
		
		Button accountBtn = ( Button ) findViewById(R.id.btn_account);
		accountBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent;
		    	  if(loggedIn) {
		    		  intent = new Intent(SettingActivity.this, AccountActivity.class);
		    	  } else {
		    		  intent = new Intent(SettingActivity.this, MainActivity.class);
	    		  }
		    	  startActivity(intent);
	    	  }
		});
		
		Button aboutBtn = ( Button ) findViewById(R.id.btn_about);		
		aboutBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
					AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
					alert.setTitle("About Gauge");
					alert.setMessage("This application was developed as an example client for Gauge, the mortgage calculator API. It was developed natively in Java using Asynchronous methods to perform all webservice requests. If your interested in developing your own mortgage calculator client application or website, why not check out the linked in the settings page.").setCancelable(false).setPositiveButton("Close", null);
					alert.show();
	    	  }
		});
		
		Button websiteBtn = ( Button ) findViewById(R.id.btn_website);		
		websiteBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://nyakeh.co.uk/gauge/"));
		    	  startActivity(intent);
	    	  }
		});
		
		Button apiBtn = ( Button ) findViewById(R.id.btn_api);		
		apiBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://nyakeh.co.uk/"));
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
