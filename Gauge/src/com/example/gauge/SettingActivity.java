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
import android.widget.Toast;

public class SettingActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		buildSideNavigation(R.layout.activity_setting);		
		
		Button accountBtn = ( Button ) findViewById(R.id.btn_account);		
		accountBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  Intent intent = new Intent(SettingActivity.this, AccountActivity.class);
		    	  startActivity(intent);
	    	  }
		});
		
		Button aboutBtn = ( Button ) findViewById(R.id.btn_about);		
		aboutBtn.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
					AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
					alert.setTitle("About Gauge");
					alert.setMessage("Hello world").setCancelable(false).setPositiveButton("Close", null);
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

}
