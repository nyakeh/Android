package com.example.preferencefragmentexample;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;

public class PreferenceFragmentExampleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction =
		fragmentManager.beginTransaction();
		Fragment1 fragment1 = new Fragment1();
		fragmentTransaction.replace(android.R.id.content, fragment1);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preference_fragment_example, menu);
		return true;
	}

}