package com.example.orientations;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class OrientationsActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d("StateInfo", "OnStart");
	}

	@Override
	public void onResume() {
		Log.d("StateInfo", "onResume");
		super.onResume();
	}
	
	@Override
	public void onPause() {
		Log.d("StateInfo", "onPause");
		super.onPause();
	}
	
	@Override
	public void onStop() {
		Log.d("StateInfo", "onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		Log.d("StateInfo", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	public void onRestart() {
		Log.d("StateInfo", "onRestart");
		super.onRestart();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.orientations, menu);
		return true;
	}

}
