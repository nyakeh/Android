package com.example.passingdata;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class PassingDataActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	public void onClick(View view) {
		Intent i = new
		Intent("net.example.PassingDataSecondActivity");
		//---use putExtra() to add new name/value pairs---
		i.putExtra("str1", "This is a string");
		i.putExtra("age1", 25);
		//---use a Bundle object to add new name/values
		// pairs---
		Bundle extras = new Bundle();
		extras.putString("str2", "This is another string");
		extras.putInt("age2", 35);
		//---attach the Bundle object to the Intent object---
		i.putExtras(extras);
		//---start the activity to get a result back---
		startActivityForResult(i, 1);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//---check if the request code is 1---
		if (requestCode == 1) {
			//---if the result is OK---
			if (resultCode == RESULT_OK) {
				//---get the result using getIntExtra()---
				Toast.makeText(this, Integer.toString(
				data.getIntExtra("age3", 0)),
				Toast.LENGTH_SHORT).show();
				//---get the result using getData()---
				Toast.makeText(this, data.getData().toString(),
				Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.passing_data, menu);
		return true;
	}

}
