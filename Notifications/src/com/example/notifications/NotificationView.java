
package com.example.notifications;

import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;

public class NotificationView extends Activity {
	int notificationID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//---look up the notification manager service---
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//---cancel the notification that we started---
		nm.cancel(getIntent().getExtras().getInt("notificationID"));
	}
}