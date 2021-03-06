package com.example.gauge;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gauge.R;

public class DrawerActivity extends Activity {
	SharedPreferences prefs;
    private DrawerLayout mDrawerLayout;
    private ListView drawerList;
    private ListView adminDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mTitle;
    private String[] menuTitles;
    private String[] adminMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
		prefs = getSharedPreferences("gauge_app", MODE_PRIVATE);
        
        mTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuTitles = getResources().getStringArray(R.array.menu_array);
        adminMenuTitles = getResources().getStringArray(R.array.login_menu_array);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        adminDrawerList = (ListView) findViewById(R.id.left_drawer_admin);

        // enable ActionBar app icon to behave as action to toggle sidebar
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close  
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    
    public void buildSideNavigation(int layoutResId) {
    	setContentView(layoutResId);
        generateLists();
    }

	public void buildSideNavigation(View resultsView) {
    	setContentView(resultsView);
        generateLists();		
	}

	private void generateLists() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuTitles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        

        adminDrawerList = (ListView) findViewById(R.id.left_drawer_admin);
        
        if(prefs.getInt("AccountId", 0) != 0) {
        	adminMenuTitles = getResources().getStringArray(R.array.account_menu_array);
        }
        adminDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.admin_drawer_list_item, adminMenuTitles));
        adminDrawerList.setOnItemClickListener(new AdminDrawerItemClickListener());
        
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
                );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if(item.getItemId() == R.id.action_settings) {
        	Intent intent = new Intent(DrawerActivity.this, SettingActivity.class);
			startActivity(intent);
        	return true;
        } else if(item.getItemId() == R.id.log_out) {
        	logOutUser();
        	return true;
        }
        return super.onOptionsItemSelected(item);
        
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	View parentView = (View) parent.getChildAt(0);
            Context parentContext = (Context) parentView.getContext();
            String activityTitle = parentContext.toString();   
            selectItem(position, activityTitle);
        }
    }

    private void selectItem(int position, String page) {
    	Intent intent = null;
    	switch(position) {
	    	case 0: 
				intent = new Intent(DrawerActivity.this, LandingActivity.class);
				break;
			case 1: 
				intent = new Intent(DrawerActivity.this, CalculateActivity.class);
				break;
			case 2: 
				intent = new Intent(DrawerActivity.this, CompareActivity.class);
				break;
    	}
    	String asd = intent.getComponent().getClassName();
    	if(!page.contains(asd)) {
    		startActivity(intent);
    	}
        drawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawers();
    }
    


    public class AdminDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	TextView tv = (TextView) view;
        	View parentView = (View) parent.getChildAt(0);
            Context parentContext = (Context) parentView.getContext();
            String activityTitle = parentContext.toString();            
        	selectAdminItem(position, tv.getText().toString(), activityTitle);
        }
    }

    private void selectAdminItem(int position, String title, String page) {
    	Intent intent;
    	switch(position) {
	    	case 0:
    			intent = new Intent(DrawerActivity.this, MainActivity.class);
	    		if(title.equals("Account")) {
	    			intent = new Intent(DrawerActivity.this, AccountActivity.class);
	    			if(!page.contains(title)) {
		    			startActivity(intent);		
		    		}
	    		} else {
	    			if(!page.contains("Main")) {
		    			startActivity(intent);		
		    		}
	    		}	    		
				break;
			case 1: 
	    		if(!page.contains("Setting")) {
					intent = new Intent(DrawerActivity.this, SettingActivity.class);
					startActivity(intent);	    			
	    		}
				break;
    	}
        drawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	public String buildErrorMessage(ArrayList<String> invalidFields) {
		String message = "Invalid input for ";
		for(int i=0; i < invalidFields.size(); i++) {
			message += invalidFields.get(i);
			if(i<invalidFields.size()-1) {
				message += " - ";
			}
		}
		return message;
	}

	public void logOutUser() {
		SharedPreferences.Editor editor = prefs.edit();
    	  editor.clear();
    	  editor.commit();
    	  Intent intent = new Intent(this.getApplicationContext(), LandingActivity.class);
    	  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	  startActivity(intent);
	}
}