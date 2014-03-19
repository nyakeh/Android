package com.example.gauge;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NavigationDrawerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_drawer);
		String[] as = {"Mortgage", "Compare", "Budget", "Borrow", "Account"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, as);
		final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout2);
		final ListView drawerList = (ListView) findViewById(R.id.drawer_list2);
		// Set the adapter for the list view -- Uses a view to get fancy looking list items 
		drawerList.setAdapter(adapter);
        // Set the list's click listener
		drawerList.setOnItemClickListener(new OnItemClickListener()
		{			 
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, final int pos, long id)
	        {
	        	Intent intent;
	        	
	        	switch(pos) {
	        		case 1:
	        		case 2:
	        		case 3:
	        		case 4:
	        			intent = new Intent(NavigationDrawerActivity.this, MainActivity.class);
	        			break;
        			default:
        				drawer.closeDrawers();
        				return;
	        	}
	        	startActivity(intent);
	        	drawer.closeDrawers();
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation_drawer, menu);
		return true;
	}

}
